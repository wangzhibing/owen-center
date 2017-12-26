package com.owen.designpatterns.structural.decorator.demo1;

import com.owen.designpatterns.structural.decorator.demo1.beverage.DarkRoastBeverage;
import com.owen.designpatterns.structural.decorator.demo1.beverage.EspressoBeverage;
import com.owen.designpatterns.structural.decorator.demo1.decorator.Mocha;
import com.owen.designpatterns.structural.decorator.demo1.decorator.Soy;
import com.owen.designpatterns.structural.decorator.demo1.decorator.Whip;

/**
 * Created by owen on 17/8/2.
 * 饮料 超类
 */
public class StarbuzzCoffee {

    public static void main(String[] args) {

        Beverage beverage1 = new EspressoBeverage();
        System.out.println(beverage1.getDescription()+" $"+beverage1.cost());

        Beverage beverage2 = new DarkRoastBeverage();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription()+" $"+beverage2.cost());

        Beverage beverage3 = new DarkRoastBeverage();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Whip(beverage3);
        System.out.println(beverage3.getDescription()+" $"+beverage3.cost());

    }


}
