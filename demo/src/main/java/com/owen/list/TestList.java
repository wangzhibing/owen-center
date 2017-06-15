package com.owen.list;

import com.owen.string.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 17/5/31.
 */
public class TestList {

    public static void main(String[] args){
        TestList  tt = new TestList();
        tt.testReference();
    }

    public void testReference(){
        List<MyBean> list  = new ArrayList<>();
        List<MyBean> list2  = new ArrayList<>();
        List<MyBean> list3  = new ArrayList<>();

        list.add(new MyBean(2,"bbb"));

        for(MyBean my : list){
            my.setName("modify1");
            list2.add(my);
        }

        System.out.println("list2:"+list2);


        List<MyBean> list_ = new ArrayList<>(list);
        for(MyBean my : list_){
            my.setName("modify3");
            list3.add(my);
        }
        System.out.println("list3:"+list3);

        System.out.println("after...");
        System.out.println("list2:"+list2);

    }

}


class MyBean{
    private Integer type;
    private String name;

    public MyBean(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "MyBean{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
