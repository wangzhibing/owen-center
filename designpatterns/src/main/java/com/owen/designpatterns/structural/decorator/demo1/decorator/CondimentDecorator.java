package com.owen.designpatterns.structural.decorator.demo1.decorator;

import com.owen.designpatterns.structural.decorator.demo1.Beverage;

/**
 * Created by owen on 17/8/2.
 * 调料装饰者 超类
 */
public abstract class CondimentDecorator extends Beverage {

    /**
     * 所有装饰者都必需重新实现getDescciption
     *
     * @return
     */
    public abstract String getDescription();
}
