package com.fakeoder.runit.core.arrange.impl;

import com.fakeoder.runit.core.arrange.AbstractArranger;

/**
 * @author zhuo
 */
public class XmlArranger extends AbstractArranger {
    private String path;

    private static XmlArranger ARRANGER;

    public static XmlArranger load(String path){
        if(ARRANGER==null) {
            ARRANGER = new XmlArranger(path);
        }
        return ARRANGER;
    }

    private void readXml(String path) {
    }

    private XmlArranger(String path){
        readXml(path);
    }
}
