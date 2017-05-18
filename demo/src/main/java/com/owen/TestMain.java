package com.owen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestMain {

    public static void main(String[] args) {
        
//        String siteIds="1001,1002,1003";
//        String[] siteArr = siteIds.split(",");
//        List<Long> siteIdList = Arrays.asList(siteArr);
//        System.out.println(siteIdList);
        
        Object obj = new Object();
        
        StudentBean bean = new StudentBean("aaa", 20);
        TestMain.change1(bean);
        System.out.println(bean.toString());
        
        Integer num = 5;
        TestMain.change2(num);
        System.out.println("num:"+num);
        
    }

    public  static void change1(StudentBean bean) {
        bean.setName("bbb");
       // bean = new StudentBean("bbb", 20);
    }
    
    public  static void change2(Integer num) {
        num = 13;
    }
}

class StudentBean {

    private String name;

    private int    age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public StudentBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "StudentBean [name=" + name + ", age=" + age + "]";
    }

}
