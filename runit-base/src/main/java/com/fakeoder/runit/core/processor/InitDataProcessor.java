package com.fakeoder.runit.core.processor;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author zhuo
 */
public class InitDataProcessor {
    private static final Logger log = Logger.getLogger(InitDataProcessor.class);

    protected String initExpression;

    public String getInitExpression() {
        return initExpression;
    }

    public void setInitExpression(String initExpression) {
        this.initExpression = initExpression;
    }

    public void init(Map<String,Object> context) {
        log.debug("action init");
    }
}
