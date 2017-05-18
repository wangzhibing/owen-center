package com.owen.arithmetic;

import java.util.Random;

public class RedPacket {

    /***
     * 发一个随机红包，100块钱给10个人。每个人最多12块钱，最少6块钱。怎么分？
     * 
     */
    public static void splitRedPacket() {
        // 设 sum=100 ， n=10 ,则题目可以得到以下结论 6n <= sum <= 12n 。
        // 设 randNum 为随机红包的大小，则可以推出 6(n-1) <= (sum-randNum) <= 12(n-1)
        // sum - 12(n-1) <= randNum <= sum - 6(n-1) 。
        // 又由 6 <= randNum <= 12 计算得到红包的上下界:
        // $min = ($sum - 12 * ($i-1))>6?($sum - 12 * ($i-1)):6;
        // $max = ($sum - 6 * ($i-1))<12?($sum - 6 * ($i-1)):12;

        int n = 10;
        int sum = 100;
        // $result = [];
        for (int i = n; i >= 1; i--) {
            int min = (sum - 12 * (i - 1)) > 6 ? (sum - 12 * (i - 1)) : 6;
            int max = (sum - 6 * (i - 1)) < 12 ? (sum - 6 * (i - 1)) : 12;
            Random random = new Random();
            int s = random.nextInt(max) % (max - min + 1) + min;
            sum -= s;
            System.out.println("s:" + s);
        }
    }
    
    
    /***
     * 发一个随机红包，100块钱给10个人。每个人最多不超过60块钱，最少1块钱。怎么分？
     * 
     */
    public static void splitRedPacket2() {
        // 设 sum=100 ， n=10 ,则题目可以得到以下结论 n <= sum <= 60n 。
        // 设 randNum 为随机红包的大小，则可以推出 6(n-1) <= (sum-randNum) <= 12(n-1)
        // sum - 12(n-1) <= randNum <= sum - 6(n-1) 。
        // 又由 6 <= randNum <= 12 计算得到红包的上下界:
        // $min = ($sum - 12 * ($i-1))>6?($sum - 12 * ($i-1)):6;
        // $max = ($sum - 6 * ($i-1))<12?($sum - 6 * ($i-1)):12;

        int n = 10;
        int sum = 100;
        // $result = [];
        for (int i = n; i >= 1; i--) {
            int min = (sum - 60 * (i - 1)) > 1 ? (sum - 60 * (i - 1)) : 1;
            int max = (sum - 1 * (i - 1)) < 60 ? (sum - 1 * (i - 1)) : 60;
            Random random = new Random();
            int s = random.nextInt(max) % (max - min + 1) + min;
            sum -= s;
            System.out.println("s:" + s);
        }
    }
    
    public static void main(String[] args) {
        RedPacket.splitRedPacket2();
    }
}
