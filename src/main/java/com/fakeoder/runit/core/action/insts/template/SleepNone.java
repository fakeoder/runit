package com.fakeoder.runit.core.action.insts.template;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;

/**
 * @author zhuo
 */
public class SleepNone extends Action {

    @Override
    public ActionResult run() {
        System.out.println("SleepNone is running");
        return new ActionResult(this.getId(),"{\"result\":{\"next\":\"1\"}}");
    }
}
