package com.owen.jvm;

import java.util.ArrayList;
import java.util.List;

public class ListSub {

    public static void main(String[] args) {
        
        
        List<Integer> arr = new ArrayList<Integer>();
        arr.add(12);
        arr.add(13);
        arr.add(3);
        arr.add(2);
        arr.add(1);
        
        arr.add(7);
        arr.add(129);
        arr.add(14);
        arr.add(128);
        arr.add(12);
        
        
        for (Integer integer : arr) {
            if(integer.intValue() > 10 ){
                arr.remove(integer);
                break;
            }
        }
        
        
        for (Integer integer : arr) {
            if(integer.intValue() > 10 ){
                arr.remove(integer);
                //break;
            }
        }
        
        System.out.println(arr.toString());
        
        
    }
    
}
