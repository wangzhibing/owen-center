package com.owen.designpatterns.factorymethod.demo1;

/**
 * Created by owen on 17/8/9.
 * PizzStore工厂
 */
public abstract class PizzStore {

    /**
     * 向工作订购一一个pizz,订一个type的pizz
     *
     * @param type
     * @return
     */
    public Pizz orderPizz(String type) {
        Pizz pizz = this.createPizz(type);
        pizz.prepare();
        pizz.bake();
        pizz.cut();
        pizz.box();
        return pizz;
    }

    /**
     * 抽象方法：创建pizz
     *
     * @param type
     * @return
     */
    public abstract Pizz createPizz(String type);
}
