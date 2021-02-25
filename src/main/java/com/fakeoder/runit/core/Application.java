package com.fakeoder.runit.core;

import com.fakeoder.runit.core.task.AbstractTask;
import com.fakeoder.runit.core.task.insts.XmlTask;

/**
 * @author zhuo
 */
public class Application {

    public static void main(String[] args) {
        AbstractTask task = new XmlTask("task-template.xml");
        task.start();
    }
}
