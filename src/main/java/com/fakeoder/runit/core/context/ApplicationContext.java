package com.fakeoder.runit.core.context;

import com.fakeoder.runit.core.action.AbstractAction;
import com.fakeoder.runit.core.action.ActionResult;
import com.fakeoder.runit.core.arrange.AbstractArranger;

import java.util.List;
import java.util.concurrent.*;

/**
 * main context, storage system config, middle result and global variables
 * @author zhuo
 */
public abstract class ApplicationContext {
    /**
     * the beginning action
     */
    private AbstractAction beginAction;

    /**
     * actions map, key is action identified, value is action
     */
    private ConcurrentHashMap<String,AbstractAction> actions;

    /**
     * arranger
     */
    private AbstractArranger arranger;

    private ThreadPoolExecutor executor;

    private void init(){
        executor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    }

    /**
     * start context and start this task
     */
    void start(String id){

        AbstractAction action;
        if(id==null){
            id = beginAction.getId();
            action = beginAction;
        }
        action = actions.get(id);



        //check begin action
        //FIXME condition create a class
        String condition = action.getPreCondition();
        boolean able2Run = able2Run(condition,arranger.getPreActionIds(id));

        //scheduled call
        Future<ActionResult> future = executor.submit(ExecutorBuilder.builder(action));
        ActionResult result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        List<String> actionIds = arranger.getRunnableActionIds(result);
        for(String actionId : actionIds){
            start(actionId);
        }

        System.out.println("task run finished!");
    }


    /**
     * this action if able to run
     * @param condition
     * @param preActionIds
     * @return
     */
    protected boolean able2Run(String condition, List<String> preActionIds){


        return true;
    }

    static class ExecutorBuilder implements Callable{
        private AbstractAction action;

        public static ExecutorBuilder builder(AbstractAction action){
            return new ExecutorBuilder(action);
        }

        public ExecutorBuilder(AbstractAction action) {
            this.action = action;
        }

        @Override
        public ActionResult call() throws Exception {
            return action.run();
        }
    }
}
