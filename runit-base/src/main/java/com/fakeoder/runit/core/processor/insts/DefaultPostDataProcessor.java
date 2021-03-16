package com.fakeoder.runit.core.processor.insts;

import com.fakeoder.runit.core.action.ActionResult;
import com.fakeoder.runit.core.processor.PostDataProcessor;
import org.apache.log4j.Logger;


/**
 * @author zhuo
 */
public class DefaultPostDataProcessor extends PostDataProcessor {
    private final static Logger log = Logger.getLogger(DefaultPostDataProcessor.class);

    @Override
    public ActionResult wrapper(ActionResult data) {
        log.debug("wrapper effective");
        return super.wrapper(data);
    }
}
