package com.owen.designpatterns.decorator.demo1.beverage;

import com.owen.designpatterns.decorator.demo1.Beverage;

/**
 * Created by owen on 17/8/2.
 * 具体的饮料： 综合咖啡，需具体定义价格，及描述
 */
public class HouseBlendBeverage extends Beverage {

    public HouseBlendBeverage() {
        //定义具体的描述
        description = "HouseBlend";
    }

    /**
     * 定义饮料地价格
     *
     * @return
     */
    @Override
    public double cost() {
        return .89;
    }
}
