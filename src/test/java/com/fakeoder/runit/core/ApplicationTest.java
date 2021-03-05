package com.fakeoder.runit.core;

import com.alibaba.fastjson.JSONObject;
import com.fakeoder.runit.core.task.AbstractTask;
import com.fakeoder.runit.core.task.insts.XmlTask;
import org.junit.Test;

public class ApplicationTest {

    @Test
    public void test1(){
        AbstractTask task = new XmlTask("task01.xml");
        task.start();
    }

    @Test
    public void test2(){
        AbstractTask task = new XmlTask("task02.xml");
        task.start();
        System.out.println(JSONObject.toJSONString(task.getContext()));
    }

    @Test
    public void test3(){
        AbstractTask task = new XmlTask("task03.xml");
        task.start();
        System.out.println(JSONObject.toJSONString(task.getContext()));
    }
}
