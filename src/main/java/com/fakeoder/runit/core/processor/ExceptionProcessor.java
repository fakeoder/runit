package com.fakeoder.runit.core.processor;

import org.apache.log4j.Logger;

/**
 * @author zhuo
 */
public class ExceptionProcessor {
    private static final Logger log = Logger.getLogger(ExceptionProcessor.class);

    public void process(Exception e){
        log.debug(e.toString());
    }
}
