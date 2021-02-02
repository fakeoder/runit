package com.fakeoder.runit.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author zhuo
 */
public class ConditionExpressionParser {

    /**
     * boolean expression
     */
    static class BooleanExpressionCalculator{
        private static final char LEFT_BRACKET = '(';
        private static final char RIGHT_BRACKET = ')';
        private static final Map<Character,Integer> NICE_OPERATION = new HashMap(8);

        static {
            NICE_OPERATION.put(')',-1);
            NICE_OPERATION.put('|',0);
            NICE_OPERATION.put('&',1);
            NICE_OPERATION.put('(',2);
        }

        /**
         * calc expression
         * @param expression eg:1&0|1&(1&0)
         * @return boolean
         */
        static boolean calcExpression(String expression){
            Stack<Character> stackExp = new Stack();
            Stack<Character> stackOpe = new Stack();

            for(char exp : expression.toCharArray()){
                if(exp=='0'||exp=='1'){
                    stackExp.push(exp);
                }else{
                    if(stackOpe.isEmpty()){
                        stackOpe.push(exp);
                    }else{
                        Character c = stackOpe.peek();
                        if(NICE_OPERATION.get(exp)>NICE_OPERATION.get(c)||c==LEFT_BRACKET){
                            stackOpe.push(exp);
                        }else{
                            if(exp==RIGHT_BRACKET){
                                char op;
                                while((op=stackOpe.pop())!=LEFT_BRACKET){
                                    char p1 = stackExp.pop();
                                    char p2 = stackExp.pop();
                                    char rc = calc(p1,p2,op);
                                    stackExp.push(rc);
                                }
                            }else {
                                stackOpe.push(exp);
                                stackCalc(stackExp,stackOpe);
                            }
                        }
                    }
                }
            }
            while(!stackOpe.isEmpty()){
                stackCalc(stackExp,stackOpe);
            }
            return stackExp.pop()=='1';
        }

        /**
         * pop 2 expression and pop 1 operation
         * @param stackExp expression stack
         * @param stackOpe operation stack
         */
        public static void stackCalc(Stack<Character> stackExp, Stack<Character> stackOpe){
            char p1 = stackExp.pop();
            char p2 = stackExp.pop();
            char rc = calc(p1, p2, stackOpe.pop());
            stackExp.push(rc);
        }

        /**
         * calc boolean expression
         * @param p1 '0' or '1'
         * @param p2 '0' or '1'
         * @param pop '&' or '|'
         * @return
         */
        private static char calc(char p1, char p2, char pop) {
            int c1 = Integer.parseInt(p1+"");
            int c2 = Integer.parseInt(p2+"");

            switch (pop){
                case '|':
                    return c1+c2==0?'0':'1';
                default:
                    return c1*c2==0?'0':'1';
            }
        }

    }

    public static void main(String[] args) {
        String expression = "1&0|(1&(0|1&1))";
        boolean rsl = BooleanExpressionCalculator.calcExpression(expression);
        System.out.println("expression:["+expression+"], result is:"+rsl);
    }
}
