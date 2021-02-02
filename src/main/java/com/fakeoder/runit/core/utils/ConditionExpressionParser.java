package com.fakeoder.runit.core.utils;

import com.fakeoder.runit.core.exception.NoSuchOperationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO ConditionCalculator: give a condition expression and environment parameters, then give you the answer
 *                           eg: ${user.age} #{gt} 15 #{&} ${user.name} #{contains} zhangSan
 * @author zhuo
 */
public class ConditionExpressionParser {


    /**
     * 1)according regex get the variables and calculate it ( > < == !=)
     * 2)transform to boolean expression
     */
    static class RegexExpressionCalculator{
        private static final String REGEX_PATTERN = "\\$\\{[a-zA-Z][a-zA-Z0-9.]*\\}";

        public static void main(String[] args) {
            Pattern pattern = Pattern.compile(REGEX_PATTERN);
            Matcher matcher = pattern.matcher("${abc.def}==${ghi.jkl}");
            int count = 0;
            while(matcher.find()){
                System.out.println(matcher.group());
                System.out.println("find :"+(++count));
            }


        }
    }



    /**
     * boolean expression
     */
    static class BooleanExpressionCalculator{
        private static final char LEFT_BRACKET = '(';
        private static final char RIGHT_BRACKET = ')';
        private static final char TRUE = '1';
        private static final char FALSE = '0';
        private static final char OR = '|';
        private static final char AND = '&';
        private static final Map<Character,Integer> NICE_OPERATION = new HashMap(8);

        static {
            NICE_OPERATION.put(RIGHT_BRACKET,-1);
            NICE_OPERATION.put(OR,0);
            NICE_OPERATION.put(AND,1);
            NICE_OPERATION.put(LEFT_BRACKET,2);
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
                if(exp==TRUE||exp==FALSE){
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
                                stackCalc(stackExp,stackOpe);
                                stackOpe.push(exp);
                            }
                        }
                    }
                }
            }
            while(!stackOpe.isEmpty()){
                stackCalc(stackExp,stackOpe);
            }
            return stackExp.pop()==TRUE;
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
                case OR:
                    return c1+c2==0?FALSE:TRUE;
                case AND:
                    return c1*c2==0?FALSE:TRUE;
                default:
                    throw new NoSuchOperationException(String.format("No such Operation:[%s]!", pop));
            }
        }

    }

    public static void main(String[] args) {
        String expression = "1&0|(1&(0|1&1))|0";
        boolean rsl = BooleanExpressionCalculator.calcExpression(expression);
        System.out.println("expression:["+expression+"], result is:"+rsl);
    }
}
