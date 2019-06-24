package com.owen.list;

import com.google.common.collect.Lists;

import javax.swing.plaf.SeparatorUI;
import java.util.List;

/**
 * @Author owen[暖风]
 * @Date 18/1/4 下午5:36
 * @Version 1.0
 */
public class ListTestDemo {

    public static void main(String[] args) {
        Base b = new Sub();
        System.out.println(b.x);
        System.out.println(b.y);
    }
}

class Base {

    int x = 10;

    int y = 11;

    public Base() {
        this.print();
        x = 20;
    }

    public void print() {
        System.out.println("Base.x = " + x);
    }
}

class Sub extends Base {

    int x = 30;
    int y = 21;

    public Sub() {
        this.print();
        x = 40;
    }

    @Override
    public void print() {
        System.out.println("Sub.x = " + x);
    }
}
