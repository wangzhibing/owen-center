package com.owen.jvm;

public class Fore {
    public static void main(String[] args) {

        // 定义一个一维数组
        int arr[] = new int[4];
        System.out.println("----未赋值前输出刚刚定义的数组----");
        for (int x : arr) {
            System.out.println(x);
        }

        // 通过索引给数组元素赋值
        System.out.println("----通过循环变量给数组元素赋值----");
        for (int i = 3; i > 0; i--) {
            arr[i] = i;
        }
        // 循环输出创建的数组
        System.out.println("----赋值后，foreach输出创建好的数组----");
        for (int x : arr) {
            System.out.println(x);
        }
    } 
}
