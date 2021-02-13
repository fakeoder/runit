package com.fakeoder.runit.core.action.insts;

import com.fakeoder.runit.core.action.AbstractAction;
import com.fakeoder.runit.core.action.ActionResult;

public class PrintAction extends AbstractAction {

    @Override
    public ActionResult run() {
        System.out.println("PrintAction is running");
        return null;
    }
}
