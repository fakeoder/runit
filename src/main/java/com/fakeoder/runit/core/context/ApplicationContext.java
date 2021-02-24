package com.fakeoder.runit.core.context;

import com.alibaba.fastjson.JSONObject;
import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;
import com.fakeoder.runit.core.action.PreCondition;
import com.fakeoder.runit.core.action.State;
import com.fakeoder.runit.core.arrange.Arranger;
import com.fakeoder.runit.core.arrange.ArrangerRule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * main context, storage system config, middle result and global variables
 * @author zhuo
 */
public abstract class ApplicationContext {
    /**
     * the context
     */
    private Map<String,Object> context;

    /**
     * the beginning action
     */
    private Action beginAction;

    /**
     * actions map, key is action identified, value is action
     */
    private ConcurrentHashMap<String, Action> actions;

    /**
     * arranger
     */
    private Arranger arranger;

    /**
     * thread pool executor
     */

    private ConcurrentHashMap<String,Future> runningActions;

    private ThreadPoolExecutor executor;

    private void init(){
        executor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    }

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
                        System.out.println("cancel action success![id="+cid+"]");
                    }
                    System.out.println("no running action![id="+cid+"]");
                }
            }

            //scheduled call
            Future<ActionResult> future = executor.submit(ExecutorBuilder.builder(action));
            ActionResult result = null;
            try {
                result = future.get();
                //todo register result
                register(result);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
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

    public static void start(){
        ApplicationContext context = new ApplicationContext(){};
        context.init();
        context.process("begin");
        System.out.println("task run finished!");
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

        return null;
    }


    /**
     * this action if able to run
     * @param condition
     * @param preActionIds
     * @return
     */
    protected boolean able2Run(PreCondition condition,String id, List<String> preActionIds){
        condition.judge(preActionIds,(actions,actionId)->{
            Action action = ((Map<String, Action>)actions).get(actionId);
            if(action.getState().getValue()<State.FINISHED.getValue()){
                return false;
            }
            //if arrange condition pass
            return arranger.canArrangePass(actionId.toString(),id, context);
        },this.actions);

        return true;
    }

    public static void main(String[] args) {
        ApplicationContext.start();
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
        public ActionResult call() throws Exception {
            return action.run();
        }
    }
}
