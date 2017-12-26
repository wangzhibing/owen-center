package com.owen.designpatterns.structural.decorator.demo1.decorator;

import com.owen.designpatterns.structural.decorator.demo1.Beverage;

/**
 * Created by owen on 17/8/2.
 * Whip 调料
 */
public class Whip extends CondimentDecorator {

    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    /**
     * 所有装饰者都必需重新实现getDescciption
     *
     * @return
     */
    @Override
    public String getDescription() {
        //System.out.println("Whip desciption");
        System.out.println(this.beverage.getDescription() + ",Whip");
        return this.beverage.getDescription() + ",Whip";
    }

    /**
     * 定义饮料地价格
     *
     * @return
     */
    @Override
    public double cost() {
        //System.out.println("Whip cost");
        return .30 + this.beverage.cost();
    }
}
