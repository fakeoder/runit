package com.fakeoder.runit.core.processor;

import org.apache.log4j.Logger;

/**
 * @author zhuo
 */
public class InitDataProcessor {
    private static final Logger log = Logger.getLogger(InitDataProcessor.class);

    private String initExpression;

    public void init() {
        log.debug("action init");
    }
}
