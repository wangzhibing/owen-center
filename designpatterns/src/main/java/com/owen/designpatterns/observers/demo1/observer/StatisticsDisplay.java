package com.owen.designpatterns.observers.demo1.observer;

import com.owen.designpatterns.observers.demo1.subject.Subject;

/**
 * Created by owen on 17/8/1.
 * 具体那个观察者对象：当前显示观察者
 */
public class StatisticsDisplay implements  Observer,DisplayElement {

    private float tempreature, humidity, pressure;
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Statistics conditions:"+tempreature+"F degree and " + humidity +"% humidity");
    }

    /**
     * 定义该观察者接口
     *
     * @param temp
     * @param humidity
     * @param pressure
     */
    @Override
    public void update(float temp, float humidity, float pressure) {
        this.tempreature =temp;
        this.humidity=humidity;
        this.pressure=pressure;
        display();
    }
}
