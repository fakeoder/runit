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
        Map<Character,Integer> map = new HashMap();
        map.put(')',-1);
        map.put('|',0);
        map.put('&',1);
        map.put('(',2);


        String expression = "1&0|(1&(0|1&1))";

        Stack<Character> stack_exp = new Stack();
        Stack<Character> stack_ope = new Stack();

        for(char exp : expression.toCharArray()){
            if(exp=='0'||exp=='1'){
                stack_exp.push(exp);
            }else{
                if(stack_ope.isEmpty()){
                    stack_ope.push(exp);
                }else{
                    Character c = stack_ope.peek();
                    if(map.get(exp)<map.get(c)){
                        stack_ope.push(exp);
                    }else{
                        if(exp==')'){
                            char op;
                            while((op=stack_ope.pop())!='('){
                                if(stack_exp.isEmpty()){
                                    return;
                                }
                                char p1 = stack_exp.pop();
                                char p2 = stack_exp.pop();
                                calc(p1,p2,op);
                            }
                        }

                        char p1 = stack_exp.pop();
                        char p2 = stack_exp.pop();
                        calc(p1,p2,stack_ope.pop());
                    }
                }
            }
        }


    }

    private static void calc(char p1, char p2, Character pop) {
        System.out.println(p1+pop+p2);
    }
}
