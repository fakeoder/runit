package com.fakeoder.runit.core.action;

import com.fakeoder.runit.core.processor.AbstractExceptionProcessor;
import com.fakeoder.runit.core.processor.AbstractInitDataProcessor;
import com.fakeoder.runit.core.processor.AbstractTimeoutProcessor;

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
     * action state
     * @see com.fakeoder.runit.core.action.State
     */
    protected State state = State.NOT_START;

    /**
     * pre actions result condition to know how to run this action
     * @see com.fakeoder.runit.core.action.PreCondition
     */
    protected String preCondition;

    /**
     * timeout
     */
    protected int timeout;

    /**
     * required parameters init processor
     */
    protected AbstractInitDataProcessor initDataProcessor;

    /**
     * what should do when timeout
     */
    protected AbstractTimeoutProcessor timeoutProcessor;

    /**
     * what should do when exception appears
     */
    protected AbstractExceptionProcessor exceptionProcessor;



    /**
     * main logic
     * @return JSON String
     * @throws Exception
     */
    public ActionResult run() throws Exception {

        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(String preCondition) {
        this.preCondition = preCondition;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public AbstractInitDataProcessor getInitDataProcessor() {
        return initDataProcessor;
    }

    public void setInitDataProcessor(AbstractInitDataProcessor initDataProcessor) {
        this.initDataProcessor = initDataProcessor;
    }

    public AbstractTimeoutProcessor getTimeoutProcessor() {
        return timeoutProcessor;
    }

    public void setTimeoutProcessor(AbstractTimeoutProcessor timeoutProcessor) {
        this.timeoutProcessor = timeoutProcessor;
    }

    public AbstractExceptionProcessor getExceptionProcessor() {
        return exceptionProcessor;
    }

    public void setExceptionProcessor(AbstractExceptionProcessor exceptionProcessor) {
        this.exceptionProcessor = exceptionProcessor;
    }
}
