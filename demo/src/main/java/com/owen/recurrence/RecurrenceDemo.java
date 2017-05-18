package com.owen.recurrence;

public class RecurrenceDemo {

    
    public static void main(String[] args) {
        System.out.println(RecurrenceDemo.recurrence1(1234567));
    }
    
    
    /***
     * 考一下你对递归的掌握。写一个函数，输入int型，返回整数逆序后的字符串。
     * 如：输入123，返回“321”。 要求必须用递归，不能用全局变量，输入必须是一个参数，必须返回字符串
     * i=12345678
     */
    public static String recurrence1(int i){
        String v = i+"";
        if(v.length() > 1){
            String str = v.substring(0, v.length()-1);
            String currv = v.substring(v.length()-1, v.length());
            String rev = recurrence1(Integer.parseInt(str));
            return currv+rev;
        }
        return v;
    }
}
