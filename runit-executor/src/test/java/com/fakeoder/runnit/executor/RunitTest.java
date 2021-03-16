package com.fakeoder.runnit.executor;

import com.alibaba.fastjson.JSONObject;
import com.fakeoder.runit.core.task.AbstractTask;
import com.fakeoder.runit.core.task.insts.XmlTask;
import org.junit.Test;

public class RunitTest {
    @Test
    public void test1(){
        AbstractTask task = new XmlTask("task01.xml");
        task.start();
        System.out.println(JSONObject.toJSONString(task.getContext()));
    }


    public static void test2(){
        AbstractTask task = new XmlTask("task02.xml");
        task.start();
        System.out.println(JSONObject.toJSONString(task.getContext()));
    }

    public static void main(String[] args) {
        test2();
    }

}
