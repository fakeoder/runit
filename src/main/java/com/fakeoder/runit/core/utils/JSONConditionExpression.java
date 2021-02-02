package com.fakeoder.runit.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author zhuo
 */
public class JSONConditionExpression {


    public boolean eval(){
        return false;
    }


    public static void main(String[] args) {


        String expression = "1&0|(1&(0|1&1))";
        System.out.println("result is :"+expression(expression));

    }
    
    static boolean expression(String expression){
        Map<Character,Integer> map = new HashMap();
        map.put(')',-1);
        map.put('|',0);
        map.put('&',1);
        map.put('(',2);


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
                    if(map.get(exp)>map.get(c)||c=='('){
                        stackOpe.push(exp);
                    }else{
                        if(exp==')'){
                            char op;
                            while((op=stackOpe.pop())!='('){
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
