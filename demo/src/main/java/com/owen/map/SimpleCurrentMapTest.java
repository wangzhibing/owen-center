package com.owen.map;


public class SimpleCurrentMapTest {

    public static void main(String[] args) {

        
            Thread a = new Thread(new Runnable() {
                public void run() {

                    for(int i=0;i<100;i++){
                        SimpleCurrentMap.add("key","okokok");
                        try {
                            Thread.sleep(5000l);
                            SimpleCurrentMap.add("key","okokok");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            a.start();
            
            Thread b = new Thread(new Runnable() {
                public void run() {
                    for(int i=0;i<100;i++){
                        System.out.println();
                        try {
                            Thread.sleep(4000l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String v = SimpleCurrentMap.get("key");
                        System.out.println("v:"+v);
                    }
                }
            });
            b.start();  

    }
}
