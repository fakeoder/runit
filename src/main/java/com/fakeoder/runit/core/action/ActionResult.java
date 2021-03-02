package com.fakeoder.runit.core.action;

/**
 * @author zhuo
 */
public class ActionResult {
    private String actionId;

    private ACTION_RESULT_STATUS status;

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

    public ACTION_RESULT_STATUS getStatus() {
        return status;
    }

    public void setSuccess() {
        this.status = ACTION_RESULT_STATUS.SUCCESS;
    }

    public void setFail() {
        this.status = ACTION_RESULT_STATUS.FAIL;
    }

    public void setTimeout() {
        this.status = ACTION_RESULT_STATUS.TIMEOUT;
    }

    enum ACTION_RESULT_STATUS{
        SUCCESS(0),FAIL(1),TIMEOUT(2);

        private int code;

        ACTION_RESULT_STATUS(int code){
            this.code = code;
        }

    }
}
