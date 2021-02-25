package com.fakeoder.runit.core.action;

/**
 * @author zhuo
 */
public class ActionResult {
    private String actionId;

    private String result;

    public ActionResult(String action, String result){
        this.actionId = action;
        this.result = result;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
