package com.fakeoder.runit.core.processor;

import com.fakeoder.runit.core.action.ActionResult;

/**
 * @author zhuo
 */
public class PostDataProcessor {

    private String expression;


    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * wrapper the action result
     * @param data
     * @return
     */
    public ActionResult wrapper(ActionResult data){
        return data;
    }
}
