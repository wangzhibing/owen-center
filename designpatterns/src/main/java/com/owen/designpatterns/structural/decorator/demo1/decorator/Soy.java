package com.owen.designpatterns.structural.decorator.demo1.decorator;

import com.owen.designpatterns.structural.decorator.demo1.Beverage;

/**
 * Created by owen on 17/8/2.
 * Soy 调料
 */
public class Soy extends CondimentDecorator {

    private Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    /**
     * 所有装饰者都必需重新实现getDescciption
     *
     * @return
     */
    @Override
    public String getDescription() {
        //System.out.println("Soy desciption");
        System.out.println(this.beverage.getDescription() + ",Soy");
        return this.beverage.getDescription() + ",Soy";
    }

    /**
     * 定义饮料地价格
     *
     * @return
     */
    @Override
    public double cost() {
        //System.out.println("Soy cost");
        return .40 + this.beverage.cost();
    }
}
