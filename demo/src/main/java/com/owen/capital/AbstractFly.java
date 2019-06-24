package com.owen.capital;

/**
 * @Author owen[暖风]
 * @Date 18/6/22 下午2:35
 * @Version 1.0
 */
public abstract class AbstractFly implements IFly {

    @Override
    public void flyHigh() {
        System.out.println("I can fly high");
    }
}
