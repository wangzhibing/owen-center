package com.owen.functionprogaming.demo2;

/**
 * @Author owen[暖风]
 * @Date 18/3/12 下午8:59
 * @Version 1.0
 */
public class Demo2Test {

    public static void main(String[] args) {

        MyLambda aBlockCode= (str) -> System.out.println(str);

        aBlockCode.out("sss");

    }

}
