package com.fakeoder.runit.core.conf;

import org.dom4j.DocumentException;

import java.net.URISyntaxException;

/**
 * @author zhuo
 */
public abstract class ConfigReader {

    /**
     * read config and build a config
     * @return
     */
    public abstract TaskConfig read() throws URISyntaxException, DocumentException;
}
