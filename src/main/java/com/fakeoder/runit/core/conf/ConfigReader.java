package com.fakeoder.runit.core.conf;

/**
 * @author zhuo
 */
public abstract class ConfigReader {

    /**
     * read config and build a config
     * @return
     */
    abstract TaskConfig read();
}
