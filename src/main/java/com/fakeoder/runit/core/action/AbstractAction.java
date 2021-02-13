package com.fakeoder.runit.core.action;

import com.fakeoder.runit.core.processor.ExceptionProcessor;
import com.fakeoder.runit.core.processor.InitDataProcessor;
import com.fakeoder.runit.core.processor.TimeoutProcessor;

/**
 * abstract action define
 * @author zhuo
 */
public abstract class AbstractAction {

    /**
     * action id
     */
    protected String id;

    /**
     * params
     */
    protected String params;

    /**
     * action result
     */
    protected ActionResult actionResult;

    /**
     * action state
     * @see com.fakeoder.runit.core.action.State
     */
    protected State state = State.NOT_START;

    /**
     * pre actions result condition to know how to run this action
     * @see com.fakeoder.runit.core.action.PreCondition
     */
    protected PreCondition preCondition;

    /**
     * timeout
     */
    protected int timeout;

    /**
     * required parameters init processor
     */
    protected InitDataProcessor initDataProcessor;

    /**
     * what should do when timeout
     */
    protected TimeoutProcessor timeoutProcessor;

    /**
     * what should do when exception appears
     */
    protected ExceptionProcessor exceptionProcessor;



    /**
     * main logic
     * @return JSON String
     * @throws Exception
     */
    public abstract ActionResult run();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public PreCondition getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(PreCondition preCondition) {
        this.preCondition = preCondition;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public InitDataProcessor getInitDataProcessor() {
        return initDataProcessor;
    }

    public void setInitDataProcessor(InitDataProcessor initDataProcessor) {
        this.initDataProcessor = initDataProcessor;
    }

    public TimeoutProcessor getTimeoutProcessor() {
        return timeoutProcessor;
    }

    public void setTimeoutProcessor(TimeoutProcessor timeoutProcessor) {
        this.timeoutProcessor = timeoutProcessor;
    }

    public ExceptionProcessor getExceptionProcessor() {
        return exceptionProcessor;
    }

    public void setExceptionProcessor(ExceptionProcessor exceptionProcessor) {
        this.exceptionProcessor = exceptionProcessor;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }
}
