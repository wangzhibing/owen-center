package com.owen.concurrence.demo;


public class UnsafeSequence {
    
    private int value;
    
    public int getNext(){
        return value++;
    }

}
