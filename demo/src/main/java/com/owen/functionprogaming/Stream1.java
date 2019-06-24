package com.owen.functionprogaming;

import com.google.common.collect.Lists;
import com.oracle.javafx.jmx.json.JSONWriter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author owen[暖风]
 * @Date 18/3/9 上午10:52
 * @Version 1.0
 */
public class Stream1 {


    public static void method1common() {
        List<Person> list = new ArrayList<>();
        Person p1 = new Person("aaa", 21);
        Person p2 = new Person("bbb", 17);
        Person p3 = new Person("ccc", 26);
        Person p4 = new Person("ddd", 25);
        Person p5 = new Person("eee", 19);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        //原始方式1
        int count = 0;
        for (Person p : list) {
            if (p.getAge() > 20) {
                count++;
            }
        }
        System.out.println("count:" + count);
    }

    public static void method2filter() {

        LinkedList<Person> list1 = new LinkedList<>();

        List<Person> list = new ArrayList<>(5);
        Person p1 = new Person("aaa", 16);
        Person p2 = new Person("bbb", 17);
        Person p3 = new Person("ccc", 26);
        Person p4 = new Person("ddd", 28);
        Person p5 = new Person("eee", 19);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        //原始方式1
        long count = list.stream().filter(person -> person.getAge() > 20).count();

        Optional<Person> tempPerson1 = list.stream().filter(person -> person.getAge() > 20).findFirst();
        Optional<Person> tempPerson2 = list.stream().filter(person -> person.getAge() > 20).max(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });

        System.out.println("count:" + count);
        System.out.println("tempPerson1:" + tempPerson1.get().getName());
        System.out.println("tempPerson2:" + tempPerson2.get().getName());
    }

    public static void method3collect() {
        List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());
        System.out.println("list=" + list);
    }

    public static void method4map() {
        List<String> list = Stream.of("a", "b", "c").map(v -> getV(v)).collect(Collectors.toList());
        System.out.println("list=" + list);
    }

    private static String getV(String k) {
        return k + "-owen";
    }

    public static void method5flatMap() {
        List<Integer> list = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9)).flatMap(integers -> integers.stream()).collect(Collectors.toList());
        System.out.println("list=" + list);
    }

    public static void method6Max() {
        List<Integer> list = Lists.newArrayList(3, 5, 2, 9, 1);
        int iv = list.stream().max(Integer::compareTo).get();
        System.out.println("list=" + iv);

        List<String> list1 = Lists.newArrayList("3", "5", "2", "19", "1");
        String sv = list1.stream().max(String::compareTo).get();
        System.out.println("list1=" + sv);
    }

    public static void method7reduce1(){
        int result = Stream.of(1,2,3,4,5).reduce(0,(acc,element)-> acc + element);
        System.out.println("result="+result);
    }

    public static void method7reduce2(){
        int result = Stream.of(1,2,3,4,5).reduce(0,(acc,element)-> addUp(element) + element);
        System.out.println("result="+result);
        //不行
    }

    private static int addUp(int element) {
        return element + 100;
    }



    public static void main(String[] args) {

        //第一学期 2017-08-01 00:00:00 2018-02-20 00:00:00
        //第二学期 2018-02-21 00:00:00 2018-07-31 00:00:00
        String startDate = "2017-08-01";
        String endDate = "2018-07-31";

       // Stream1.method7reduce2();
    }


}


class Person {
    private String name;

    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
}
