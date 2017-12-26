package com.owen.designpatterns.factorymethod.demo1;

/**
 * Created by owen on 17/8/9.
 */
public class Main {


    public static void main(String[] args) {
        PizzStore nyPizzStore = new NYPizzStore();
        PizzStore chicagoPizzStore = new ChicagoPizzStore();


        Pizz pizz = nyPizzStore.orderPizz("cheese");
        System.out.println(pizz.getName());

        pizz = chicagoPizzStore.orderPizz("clam");
        System.out.println(pizz.getName());
    }
}
