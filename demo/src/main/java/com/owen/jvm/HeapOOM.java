package com.owen.jvm;

import java.util.ArrayList;
import java.util.List;

/***
 * 测试堆内存溢出
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 
 * @author owen
 *
 */
public class HeapOOM {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<Object>();
        while (true) {
            list.add(new Object());

        }
    }

}
