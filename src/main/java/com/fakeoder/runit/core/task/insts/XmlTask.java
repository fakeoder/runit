package com.fakeoder.runit.core.task.insts;

import com.fakeoder.runit.core.conf.TaskConfig;
import com.fakeoder.runit.core.conf.insts.XmlConfigReader;
import com.fakeoder.runit.core.task.AbstractTask;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.dom4j.DocumentException;

import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuo
 */
public class XmlTask extends AbstractTask {
    Logger log = LoggerFactory.getLogger(XmlTask.class);
    private String path;

    public XmlTask(String path){
        this.path = path;
    }

    @Override
    protected void init() {
        executor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        reader = new XmlConfigReader(path);
        TaskConfig taskConfig = null;
        try {
            taskConfig = reader.read();
        } catch (URISyntaxException | DocumentException e) {
            log.error("load task config error:"+e);
            throw new RuntimeException(e);
        }
        this.runningActions = new ConcurrentHashMap<>();
        this.actions = taskConfig.getActionsAsMap();
        this.arranger = taskConfig.getArranger();
        this.context = taskConfig.getContext();
        this.beginAction = actions.get(taskConfig.getBeginAction());

    }
}
