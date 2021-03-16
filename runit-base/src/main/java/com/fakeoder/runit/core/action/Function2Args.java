package com.fakeoder.runit.core.action;

/**
 * @author zhuo
 */
@FunctionalInterface
public interface Function2Args<T,A,R> {

    /**
     * @param t
     * @param a
     * @return
     */
    R apply(T t, A a);

}
