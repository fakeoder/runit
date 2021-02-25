package com.fakeoder.runit.core.action;

import java.util.List;

/**
 * @author zhuo
 */

public enum PreCondition {

    /**
     * the first action's pre action condition
     */
    PASS(){
        @Override
        public boolean judge(List data, Function2Args wrapper, Object wrapperParam) {
            return true;
        }
    },
    /**
     * run it when pre actions is finished
     */
    ALL(){
        @Override
        public boolean judge(List data, Function2Args wrapper, Object wrapperParam) {
            return data.stream().allMatch(dat->{
                return (boolean) wrapper.apply(wrapperParam,dat);
            });
        }
    },
    /**
     * if one of pre actions finished, other killed, this action start run
     */
    ANY(){
        @Override
        public boolean judge(List data, Function2Args wrapper, Object wrapperParam) {
            return data.stream().anyMatch(dat-> (boolean) wrapper.apply(wrapperParam,dat));
        }
    };

    public abstract boolean judge(List data, Function2Args wrapper, Object wrapperParam);
}


