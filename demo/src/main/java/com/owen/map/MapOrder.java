package com.owen.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * 排序
 * @author owen
 *
 */
public class MapOrder{
    
    public static void main(String[] args) throws Exception {
        MapOrder.useHashMap();
        MapOrder.useTreeMap();
        MapOrder.useLikedHashMap();
    }
     
      
    public static void useHashMap() throws Exception {      
        System.out.println("------无序（随机输出）------");  
        Map<String, String> map = new HashMap<String, String>();      
        map.put("1", "Level 1");  
        map.put("3", "Level 3"); 
        map.put("2", "Level 2");      
             
        map.put("a", "Level a");      
        map.put("b", "Level b");      
        map.put("c", "Level c");  
        Iterator<Entry<String, String>> it = map.entrySet().iterator();      
        while (it.hasNext()) {       
            Entry<String, String> e = it.next();       
            System.out.println("Key: " + e.getKey() + ";   Value: "       + e.getValue());      
        }  
    }  
      
    // 有序(默认排序，不能指定)   
    public static void useTreeMap() throws Exception {      
        System.out.println("------有序（但是按默认顺充，不能指定）------");      
        Map<String, String> map = new TreeMap<String, String>();      
        map.put("1", "Level 1");      
             
        map.put("3", "Level 3");   
        
        map.put("2", "Level 2");
        map.put("a", "Level a");      
        map.put("b", "Level b");      
        map.put("c", "Level c");      
        Iterator<Entry<String, String>> it = map.entrySet().iterator();      
        while (it.hasNext()) {       
            Entry<String, String> e = it.next();       
            System.out.println("Key: " + e.getKey() + ";   Value: "       + e.getValue());      
        }  
    }  
      
    public static void useLikedHashMap() throws Exception {      
        System.out.println("------有序（根据输入的顺序输出）------");      
        Map<String, String> map = new LinkedHashMap<String, String>();      
        map.put("1", "Level 1");      
         
        map.put("3", "Level 3");      
        map.put("2", "Level 2");    
        map.put("a", "Level a");      
        map.put("b", "Level b");      
        map.put("c", "Level c");  
        Iterator<Entry<String, String>> it = map.entrySet().iterator();      
        while (it.hasNext()) {       
            Entry<String, String> e = it.next();       
            System.out.println("Key: " + e.getKey() + ";   Value: "       + e.getValue());      
        }  
    }  

}
