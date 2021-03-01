package com.fakeoder.runit.core.processor;

import org.apache.log4j.Logger;

/**
 * @author zhuo
 */
public class TimeoutProcessor {
    private static final Logger log = Logger.getLogger(TimeoutProcessor.class);

    public void process() {
        log.debug("action timeout");
    }
}
