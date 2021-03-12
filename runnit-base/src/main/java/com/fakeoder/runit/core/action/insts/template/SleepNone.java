package com.fakeoder.runit.core.action.insts.template;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;
import org.apache.log4j.Logger;

/**
 * @author zhuo
 */
public class SleepNone extends Action {
    private static final Logger log = Logger.getLogger(SleepNone.class);

    @Override
    public ActionResult run() {
        log.debug("SleepNone is running");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return new ActionResult(this.getId(),"{\"result\":{\"next\":\"1\"}}");
    }
}
