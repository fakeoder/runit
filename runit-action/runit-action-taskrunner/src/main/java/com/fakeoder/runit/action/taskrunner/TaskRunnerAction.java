package com.fakeoder.runit.action.taskrunner;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;
import com.fakeoder.runit.core.task.AbstractTask;
import com.fakeoder.runit.core.task.insts.XmlTask;
import org.apache.log4j.Logger;

/**
 * @author zhuo
 */
public class TaskRunnerAction extends Action {

    Logger log = Logger.getLogger(TaskRunnerAction.class);
    private final static String TASK_CONFIG_PATH = "task-path";

    @Override
    public ActionResult run() {
        Object path = context.get(TASK_CONFIG_PATH);
        if(path==null){
            String errMsg = "the required param is not set:{"+TASK_CONFIG_PATH+"}";
            log.error(errMsg);
            return new ActionResult(this.id, errMsg);
        }
        AbstractTask task = new XmlTask(path.toString());
        task.start();
        return new ActionResult(this.id, String.format("task [%s] run successful",task.getName()));
    }
}
