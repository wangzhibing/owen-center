package com.owen.designpatterns.factorymethod.demo1;

/**
 * Created by owen on 17/8/9.
 *  开一家NY的pizz店
 */
public class NYPizzStore  extends  PizzStore{

    /**
     * 抽象方法：创建pizz
     *
     * 在NY店开始制作一个pizz
     *
     * @param type
     * @return
     */
    @Override
    public Pizz createPizz(String type) {
        if(type.equals("cheese")){
            return new NYStyleCheesePizz();
        }else if(type.equals("clam")){
            return new NYStyleClamPizz();
        }
        return null;
    }
}
