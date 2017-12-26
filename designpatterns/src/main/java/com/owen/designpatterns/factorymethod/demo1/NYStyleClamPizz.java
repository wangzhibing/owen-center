package com.owen.designpatterns.factorymethod.demo1;

/**
 * Created by owen on 17/8/9.
 */
public class NYStyleClamPizz extends Pizz {

    public NYStyleClamPizz() {
        name = "Ny style Clam";
        dough = "Dough";
        sauce = "Sauce";

        toppings.add("Grated Clam");
    }
}
