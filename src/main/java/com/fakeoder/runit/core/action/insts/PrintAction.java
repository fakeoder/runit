package com.fakeoder.runit.core.action.insts;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;

/**
 * @author zhuo
 */
public class PrintAction extends Action {

    @Override
    public ActionResult run() {
        System.out.println("hello world!");
        return null;
    }
}
