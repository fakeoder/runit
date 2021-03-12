package com.fakeoder.runit.action.keylistener;

import com.alibaba.fastjson.JSONObject;
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
        Map<String, JSONObject> configs = (Map<String, JSONObject>) context.get(KEY_AND_LISTENER_CONFIG);
        for(Map.Entry<String,JSONObject> config : configs.entrySet()){
            provider.register(KeyStroke.getKeyStroke(config.getKey()),hotKey -> config.getValue().toJavaObject(Action.class).run());
        }
        return new ActionResult(this.getId(),"keyListener is active!");
    }


}
