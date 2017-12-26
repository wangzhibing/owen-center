package com.owen.designpatterns.factorymethod.demo1;

import java.util.ArrayList;

/**
 * Created by owen on 17/8/9.
 * 定义一个抽象的pizz类
 * 拿到pizz之后，开始准备、包装、切入、烘培等：准备，bake,cut,box etc
 */
public abstract class Pizz {

    String name;
    String dough;
    String sauce;
    ArrayList toppings = new ArrayList();


    void prepare() {
        System.out.println("pizz--prepare");
        System.out.println("prepareing " + name);
        for (int i = 0; i < toppings.size(); i++) {
            System.out.println("  " + toppings.get(i));
        }
    }

    void bake() {
        System.out.println("pizz--bake");
    }

    void cut() {
        System.out.println("pizz--cut");
    }

    void box() {
        System.out.println("pizz--box");
    }

    public String getName() {
        return name;
    }
}
