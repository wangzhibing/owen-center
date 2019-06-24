package com.owen.generics;

import java.util.ArrayList;

/**
 * 泛型
 *
 * @Author owen[暖风]
 * @Date 18/3/5 下午4:12
 * @Version 1.0
 */
public class Generics {

    public static void main(String[] args) {
        Generics generics = new Generics();
        generics.typeErasure();
    }


    /**
     * 类型擦除
     */
    public void typeErasure() {
        Class c1 = new ArrayList<Integer>().getClass();
        Class c2 = new ArrayList<Long>().getClass();
        System.out.println(c1 == c2);
    }
}
