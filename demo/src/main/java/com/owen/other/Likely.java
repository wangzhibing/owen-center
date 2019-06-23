package com.owen.other;

import java.util.Arrays;
import java.util.Random;

public class Likely {

    public static void main(String[] args) {
        int arraySize = 32768;
        int array[] = new int[arraySize];

        Random rnd = new Random(0);
        for (int i = 0; i < arraySize; i++)
            array[i] = rnd.nextInt() % 256;

        //排序或不排序，程序性能相差2.4倍，c语言实现性能相差3倍
        Arrays.sort(array);

        long start = System.nanoTime();
        long sum = 0;

        for (int i = 0; i < 100000; ++i) {
            for (int c = 0; c < arraySize; ++c) {
                if (array[c] >= 128)
                    sum += array[c];
            }
        }
        long end = System.nanoTime();
        System.out.printf("start:%d ns,end:%d ns,duration=%f 秒\n",start,end,(end - start) / 1000000000.0);
        System.out.println("sum = " + sum);
    }
}


