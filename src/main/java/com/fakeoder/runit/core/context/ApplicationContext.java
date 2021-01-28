package com.fakeoder.runit.core.context;

import com.fakeoder.runit.core.action.AbstractAction;
import com.fakeoder.runit.core.arrange.AbstractArranger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * start context and start this task
     */
    void start(String id){
        AbstractAction action;
        if(id==null){
            action = beginAction;
        }
        action = actions.get(id);

        //TODO init application variables


        //scheduled call



        //check begin action


        List<String> actionIds = arranger.getRunnableActionId(null);
        for(String actionId : actionIds){
            start(actionId);
        }

        System.out.println("task run finished!");
    }
}
