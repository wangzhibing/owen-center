package com.owen.designpatterns.factorymethod.demo1;

/**
 * Created by owen on 17/8/9.
 * 在芝加哥开一家pizzStore
 */
public class ChicagoPizzStore extends PizzStore {

    /**
     * 抽象方法：创建pizz
     *
     * @param type
     * @return
     */
    @Override
    public Pizz createPizz(String type) {
        if(type.equals("cheese")){
            return new ChicagoStyleCheesePizz();
        }
        return null;
    }
}
