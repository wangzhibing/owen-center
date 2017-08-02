package com.owen.designpatterns.observers.demo1.subject;

import com.owen.designpatterns.observers.demo1.observer.Observer;

/**
 * Created by owen on 17/8/1.
 * 定义主题：出版者
 */
public interface Subject {

    /**
     * 观察者注册(订阅)该主题(报社)
     * @param o
     */
    void registerObserver(Observer o);

    /**
     * 观察者取消(删除)该主题(报社)
     * @param o
     */
    void removeObserver(Observer o);

    /**
     * 主题状态改变时，该方法被调用，通知所有的观察者。
     */
    void notifyObserver();

}
