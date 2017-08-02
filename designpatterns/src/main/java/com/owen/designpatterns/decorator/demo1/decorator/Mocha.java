package com.owen.designpatterns.decorator.demo1.decorator;

import com.owen.designpatterns.decorator.demo1.Beverage;

/**
 * Created by owen on 17/8/2.
 * mocha 调料
 */
public class Mocha extends CondimentDecorator {

    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    /**
     * 所有装饰者都必需重新实现getDescciption
     *
     * @return
     */
    @Override
    public String getDescription() {
        //System.out.println("Mocha desciption");
        System.out.println(this.beverage.getDescription() + ",Mocha");
        return this.beverage.getDescription() + ",Mocha";
    }

    /**
     * 定义饮料地价格
     *
     * @return
     */
    @Override
    public double cost() {
        //System.out.println("Mocha cost");
        return .20 + this.beverage.cost();
    }
}
