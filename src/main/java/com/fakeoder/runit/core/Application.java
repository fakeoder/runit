package com.fakeoder.runit.core;

import com.alibaba.fastjson.JSONObject;
import com.fakeoder.runit.core.task.AbstractTask;
import com.fakeoder.runit.core.task.insts.XmlTask;
import org.apache.log4j.Logger;

/**
 * @author zhuo
 */
public class Application {
    static Logger log = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        AbstractTask task = new XmlTask("task-template.xml");
        task.start();
        log.info("context:"+JSONObject.toJSONString(task.getContext()));
        log.info("actions:"+JSONObject.toJSONString(task.getActions()));
        log.info("arrange:"+JSONObject.toJSONString(task.getArranger()));
    }
}
