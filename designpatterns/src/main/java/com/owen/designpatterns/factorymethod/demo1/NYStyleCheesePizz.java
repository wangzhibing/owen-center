package com.owen.designpatterns.factorymethod.demo1;

/**
 * Created by owen on 17/8/9.
 */
public class NYStyleCheesePizz extends Pizz {

    public NYStyleCheesePizz() {
        name = "Ny style Cheese";
        dough = "Dough";
        sauce = "Sauce";

        toppings.add("Grated Cheese");
    }
}
