package com.owen.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author owen[暖风]
 * @Date 18/6/16 下午10:24
 * @Version 1.0
 */
public class ViewTest {


    /**
     * 有一个字符串它的构成是词+空格的组合，如“北京 杭州 杭州 北京”， 要求输入一个匹配模式（简单的以字符来写）， 比如 aabb, 来判断该字符串是否符合该模式， 举个例子：
     * 1. pattern = "abba", str="北京 杭州 杭州 北京" 返回 true
     * 2. pattern = "aabb", str="北京 杭州 杭州 北京" 返回 false
     * 3. pattern = "baab", str="北京 杭州 杭州 北京" 返回 true
     */

    public static void main(String[] args) {

        //String pattern = "abba", strTarget = "北京 杭州 杭州 北京"; // 返回 true
        //String pattern = "aabb", strTarget="北京 杭州 杭州 北京"; //返回 false
        //String pattern = "baab", strTarget = "北京 杭州 杭州 北京"; //返回 true
        String pattern = "baba", strTarget = "北京 杭州 北京 杭州"; //返回 true
        boolean matchFlag = strPattern(pattern, strTarget);
        System.out.println("matchFlag:" + matchFlag);
    }

    /**
     * 匹配功能
     * @param pattern 匹配格式
     * @param strTarget 匹配目标
     * @return 布尔 是否匹配成功
     */
    public static boolean strPattern(String pattern, String strTarget) {

        //check null,不用第三方工具类
        if (null == pattern || "".equals(pattern) ||
                null == strTarget || "".equals(strTarget)) {
            return false;
        }

        //check format 长度校验
        String[] targetArr = strTarget.split(" ");
        int targetArrSize = targetArr.length;
        if (targetArrSize != pattern.length()) {
            return false;
        }

        //可以通过一一映射，如：a-北京,b-杭州
        //这块用for循环,有下标索引,
        //condition: 保证最开始入pTargetMap对象的 key value ,要和后续的是一致，若这条件不满足，就返回false.
        Map<Character, String> pTargetMap = new HashMap<>(targetArrSize);
        for (int i = 0; i < targetArrSize; i++) {
            String target = targetArr[i];
            Character p = pattern.charAt(i);
            String tempTarget = pTargetMap.get(p);
            if (null == tempTarget || "".equals(tempTarget)) {
                pTargetMap.put(p, target);
            } else {
                if (!tempTarget.equals(target)) {
                    return false;
                }
            }
        }
        return true;
    }
}
