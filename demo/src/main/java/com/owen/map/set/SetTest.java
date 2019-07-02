package com.owen.map.set;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mizlicai on 2019/7/2.
 */
public class SetTest {

    public static void main(String[] args) {
        SetTest.methods1();
    }



    public static void methods1(){

        Set<String>  setOrigin  = new HashSet<>();
        setOrigin.add("aaa");
        setOrigin.add("bbb");
        setOrigin.add("ccc");
        setOrigin.add("ddd");

        Set<String> setTarget =  new HashSet<>(setOrigin);
        System.out.println("setTarget:"+ setTarget);
        setOrigin.clear();
        System.out.println("setTarget:"+ setTarget);




    }
}
