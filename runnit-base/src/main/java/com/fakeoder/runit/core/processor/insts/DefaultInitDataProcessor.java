package com.fakeoder.runit.core.processor.insts;

import com.fakeoder.evall.core.Expression;
import com.fakeoder.runit.core.processor.InitDataProcessor;

import java.util.Map;

/**
 * @author zhuo
 */
public class DefaultInitDataProcessor extends InitDataProcessor {

    @Override
    public void init(Map<String,Object> context){
        Expression.eval(this.initExpression, context);
    }
}
