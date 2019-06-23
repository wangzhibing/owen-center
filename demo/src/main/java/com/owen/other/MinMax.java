package com.owen.other;

import java.util.Random;

public class MinMax {
    public static void main(String[] args) {
        Random rnd = new Random(0);
        int arraySize = 32768;
        int a[] = new int[arraySize];
        int b[] = new int[arraySize];
        int c[] = new int[arraySize];
        int d[] = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            a[i] = rnd.nextInt();
            b[i] = rnd.nextInt();
        }
        //两只minmax性能相差2倍多，c语言实现性能相差3倍
        Data trans = new Data();
        //Control trans = new Control();
        long start = System.nanoTime();
        for (int i = 0; i < 1024; i++) {
            trans.minmax(a, b, c, d, arraySize);
        }

        long end = System.nanoTime();
        System.out.printf("start:%d ns,end:%d ns,duration:%f ms\n", start, end, (end - start)/ 1000000.0);//CLOCKS_PER_SEC
    }
}

class Data{
    void minmax(int a[], int b[],int c[], int d[], int n) {
        for (int i = 0; i < n; i++) {
            int min = a[i] < b[i] ? a[i] : b[i];
            int max = a[i] < b[i] ? b[i] : a[i];
            c[i] = min;
            d[i] = max;
        }
    }
}

class Control{
    void minmax(int a[], int b[], int c[], int d[],int n) {
        for (int i = 0; i < n; i++) {
            if (a[i] > b[i]) {
                c[i] = b[i];
                d[i] = a[i];
            }else{
                c[i] = a[i];
                d[i] = b[i];
            }
        }
    }
}
