package com.owen.designpatterns.structural.decorator.demo1;

/**
 * Created by owen on 17/8/2.
 * 饮料 超类
 */
public abstract class Beverage {

    protected String description = "Ubknow Beverage";

    /**
     * 饮料的描述
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 定义饮料地价格
     *
     * @return
     */
    public abstract double cost();


}
