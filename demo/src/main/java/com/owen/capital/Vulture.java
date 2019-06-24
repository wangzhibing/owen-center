package com.owen.capital;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * @Author owen[暖风]
 * @Date 18/6/22 下午2:36
 * @Version 1.0 雕
 */
public class Vulture implements IFly {

    private IFly iFly;

    public Vulture() {
        iFly = new FlyImpl();
    }

    @Override
    public void flyHigh() {
        iFly.flyHigh();

    }

    public static void main(String[] args) {
        Vulture v = new Vulture();
        v.flyHigh();
    }

    /**
     *
     */
    class FlyImpl extends AbstractFly {
    }
}
