package com.owen.designpatterns.structural.decorator.demo1.beverage;

import com.owen.designpatterns.structural.decorator.demo1.Beverage;

/**
 * Created by owen on 17/8/2.
 * 具体的饮料：浓缩咖啡 需具体定义价格，及描述
 */
public class EspressoBeverage extends Beverage {

    public EspressoBeverage() {
        //定义具体的描述
        description = "Espresso";
    }

    /**
     * 定义饮料地价格
     *
     * @return
     */
    @Override
    public double cost() {
        return 1.99;
    }
}
