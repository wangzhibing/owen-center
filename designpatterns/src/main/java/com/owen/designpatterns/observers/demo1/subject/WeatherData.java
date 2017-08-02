package com.owen.designpatterns.observers.demo1.subject;

import com.owen.designpatterns.observers.demo1.observer.Observer;

import java.util.ArrayList;

/**
 * Created by owen on 17/8/1.
 * 主题的实现者
 */
public class WeatherData implements Subject {

    private ArrayList<Observer> observers;

    private float tempreature, humidity, pressure;

    public WeatherData() {
        this.observers = new ArrayList<>();
    }

    /**
     * 观察者注册(订阅)该主题(报社)
     *
     * @param o
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    /**
     * 观察者取消(删除)该主题(报社)
     *
     * @param o
     */
    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    /**
     * 主题状态改变时，该方法被调用，通知所有的观察者。
     */
    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update(tempreature, humidity, pressure);
        }
    }

    /**
     * 通知所有的观察者
     */
    public void measurementChanged() {
        this.notifyObserver();
    }

    /**
     * 设置状态变更触发点
     * @param temp
     * @param humidity
     * @param pressure
     */
    public void serMeasurements(float temp, float humidity, float pressure) {
        this.tempreature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.measurementChanged();
    }


}
