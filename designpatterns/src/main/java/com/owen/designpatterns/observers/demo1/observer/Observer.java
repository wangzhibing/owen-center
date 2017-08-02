package com.owen.designpatterns.observers.demo1.observer;

/**
 * Created by owen on 17/8/1.
 * 观察者－订阅者
 */
public interface Observer {

    /**
     * 定义该观察者接口
     * @param temp
     * @param humidity
     * @param pressure
     */
    public void update(float temp, float humidity, float pressure);
}
