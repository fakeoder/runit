package com.fakeoder.runit.core.task;

import com.alibaba.fastjson.JSONObject;
import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;
import com.fakeoder.runit.core.action.PreCondition;
import com.fakeoder.runit.core.action.State;
import com.fakeoder.runit.core.arrange.Arranger;
import com.fakeoder.runit.core.arrange.ArrangerRule;
import com.fakeoder.runit.core.conf.ConfigReader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * main context, storage system config, middle result and global variables
 * @author zhuo
 */
public abstract class AbstractTask {
    private static final Logger log = Logger.getLogger(AbstractTask.class);

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * the context
     */
    protected Map<String,Object> context;

    /**
     * the beginning action
     */
    protected Action beginAction;

    /**
     * actions map, key is action identified, value is action
     */
    protected ConcurrentHashMap<String, Action> actions;

    /**
     * arranger
     */
    protected Arranger arranger;

    /**
     * thread pool executor
     */
    protected ThreadPoolExecutor executor;

    protected ConcurrentHashMap<String,Future> runningActions;

    protected ConfigReader reader;

    /**
     * load configs
     */
    protected abstract void init();

    /**
     * start context and start this task
     */
    void process(String id){
        Action action;
        if(id==null){
            id = beginAction.getId();
            action = beginAction;
        }else {
            action = actions.get(id);
        }

        //check begin action
        PreCondition condition = action.getPreCondition();
        List<String> preIds = arranger.getPreActionIds(id);
        boolean able2Run = able2Run(condition,id,arranger.getPreActionIds(id));
        if(able2Run) {
            //check if need to cancel pre task
            if(condition.equals(PreCondition.ANY)) {
                List<String> cancelIds = shouldCancelPreAction(id, preIds);
                Iterator<String> iterator = cancelIds.iterator();
                while(iterator.hasNext()){
                    String cid = iterator.next();
                    Future future = runningActions.get(cid);
                    if(future!=null){
                        future.cancel(true);
                        runningActions.remove(cid);
                        log.debug("cancel action success![id="+cid+"]");
                    }
                    log.debug("no running actions!");
                }
            }

            //scheduled call
            Future<ActionResult> future = executor.submit(ExecutorBuilder.builder(action));
            runningActions.put(action.getId(),future);
            ActionResult result;
            try {
                result = future.get();
                runningActions.remove(action.getId());
                //todo register result
                register(result);

            } catch (InterruptedException | ExecutionException e) {
                log.error(e.toString());
                throw new RuntimeException(e);
            }

            List<String> actionIds = arranger.getRunnableActionIds(id);
            for (String actionId : actionIds) {
                process(actionId);
            }
        }
    }

    /**
     * register
     * @param result
     */
    protected void register(ActionResult result){
        context.put(result.getActionId(), JSONObject.parseObject(result.getResult()));
    }

    public void start(){
        log.info("init task start:");
        init();
        log.info("init task successful:" + this.getName() + "("+this.getDescription()+")");
        log.info("task process start:" + this.getName());
        process(beginAction.getId());
        log.info("task process end:" + this.getName());
    }

    /**
     * should cancel pre action
     * when current action is in "OR" PreCondition and one pre action is passed
     * @see PreCondition
     * @param id
     * @param preIds
     * @return
     */
    protected List<String> shouldCancelPreAction(String id, List<String> preIds){
        List<String> ret = new ArrayList<>();
        for(String preId : preIds){
            ArrangerRule rule = arranger.getArrangeRule(preId, id);
            rule.setCanceled(true);
            List<ArrangerRule> rules = arranger.getArrangeRulesByPre(preId);
            if(rules.stream().allMatch(r-> r.isCanceled())){
                ret.add(preId);
            }
        }

        return ret;
    }


    /**
     * this action if able to run
     * @param condition
     * @param preActionIds
     * @return
     */
    protected boolean able2Run(PreCondition condition,String id, List<String> preActionIds){
        if(preActionIds.isEmpty()){
            return true;
        }
        return condition.judge(preActionIds,(actions,actionId)->{
            Action action = ((Map<String, Action>)actions).get(actionId);
            if(action.getState().getValue()<State.FINISHED.getValue()){
                return false;
            }
            //if arrange condition pass
            return arranger.canArrangePass(actionId.toString(),id, context);
        },this.actions);
    }

    static class ExecutorBuilder implements Callable{
        private Action action;

        public static ExecutorBuilder builder(Action action){
            return new ExecutorBuilder(action);
        }

        public ExecutorBuilder(Action action) {
            this.action = action;
        }

        @Override
        public ActionResult call() {
            ActionResult result = new ActionResult(action.getId(),"success");
            try{
                action.setRunningStatus();
                action.getInitDataProcessor().init();
                long start = System.nanoTime();
                result = action.run();
                if(System.nanoTime()-start>action.getTimeout()*1000000){
                    action.getTimeoutProcessor().process();
                }
                action.setFinishedStatus();
            }catch (Exception e){
                action.getExceptionProcessor().process(e);
                action.setErrorStatus();
            }
            return result;
        }
    }

}
