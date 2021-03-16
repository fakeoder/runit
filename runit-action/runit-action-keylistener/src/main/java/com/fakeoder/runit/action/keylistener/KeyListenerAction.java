package com.fakeoder.runit.action.keylistener;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;
import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;
import java.util.Map;

/**
 * @author zhuo
 */
public class KeyListenerAction extends Action {

    private final static String KEY_AND_LISTENER_CONFIG = "keyAndListener";

    @Override
    public ActionResult run() {
        Provider provider = Provider.getCurrentProvider(false);
        Map<String, Action> configs = (Map<String, Action>) context.get(KEY_AND_LISTENER_CONFIG);
        for(Map.Entry<String,Action> config : configs.entrySet()){
            provider.register(KeyStroke.getKeyStroke(config.getKey()), hotKey -> config.getValue().run());
        }
        return new ActionResult(this.getId(),"keyListener is active!");
    }


}
