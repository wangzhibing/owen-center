package com.owen.designpatterns.factorymethod.demo1;

/**
 * Created by owen on 17/8/9.
 */
public class ChicagoStyleCheesePizz extends Pizz {

    public ChicagoStyleCheesePizz() {
        name = "Chicago style Cheese";
        dough = "Dough";
        sauce = "Sauce";

        toppings.add("Grated Cheese");
    }

    void cut(){
        //重写父类的方法
        System.out.println("Chicago。。。。。cut");
    }
}
