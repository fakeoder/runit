package com.fakeoder.runit.core.action;

/**
 * @author zhuo
 */

public enum PreCondition {

    /**
     * run it when pre actions is finished
     */
    AND(),
    /**
     * if one of pre actions finished, other killed, this action start run
     */
    OR();
}


