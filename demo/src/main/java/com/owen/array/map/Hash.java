package com.owen.array.map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Hash {

    public static void main(String[] args) {
        
        
        HashMap<String, String> hashMap = new HashMap<String,String>();
        //hashMap.put(key, value)
        
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("aaaa", "aaaa");
        concurrentHashMap.put("bbbb", "bbbb");
        concurrentHashMap.put("cccc", "cccc");
        concurrentHashMap.put("dddd", "dddd");
        concurrentHashMap.put("eeee", "eeee");
        concurrentHashMap.put("rrrr", "rrrr");
        concurrentHashMap.put("ffff", "ffff");
        concurrentHashMap.put("gggg", null);
        
        System.out.println(concurrentHashMap.get("ffff"));
        
        System.out.println(concurrentHashMap.size());
        

    }
}
