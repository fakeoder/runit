package com.fakeoder.runit.core.action.insts.template;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;
import org.apache.log4j.Logger;

/**
 * @author zhuo
 */
public class SleepNeat extends Action {
    private static final Logger log = Logger.getLogger(SleepNeat.class);

    @Override
    public ActionResult run() {
        log.debug("SleepNeat is running");
        return new ActionResult(this.getId(),"{\"name\":\"lisi\"}");
    }
}
