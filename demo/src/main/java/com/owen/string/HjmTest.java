package com.owen.string;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class HjmTest {

    public static void main(String[] args) {
        String str1 = "012126,012520,016564,018011,018049,021033,022511,022837,024495", 
               str2 = "106121,067153,067133,067159,068765,107490,081015,046071,016564,018011";
        HjmTest.compareStr(str1, str2);
    }

    public static void compareStr(String str1, String str2) {

        String[] str1Arr = str1.split(",");
        String[] str2Arr = str2.split(",");
        

        List<String> str1List = Arrays.asList(str1Arr);
        List<String> str2List = Arrays.asList(str2Arr);

        //交集  
        List<String> insterList =  (List<String>) CollectionUtils.intersection(str1List, str2List);
        System.out.println(insterList);
        
        // str1List 与 str2List差 (str1List有，str2List没有)
        List<String> subStr1List =  (List<String>) CollectionUtils.subtract(str1List, str2List);
        System.out.println(subStr1List);
        
        // str2List 与 str1List差 (str2List有，str1List没有)
        List<String> subStr2List =  (List<String>) CollectionUtils.subtract(str2List, str1List);
        System.out.println(subStr2List);
        
        // str1List.CollectionUtils.union(a, b)(并集): {1,2,3,3,4,4,5,6,7}
        // CollectionUtils.intersection(a, b)(交集): {3,4,5}
        // CollectionUtils.disjunction(a, b)(交集的补集): {1,2,3,4,6,7}
        // CollectionUtils.disjunction(b, a)(交集的补集): {1,2,3,4,6,7}
        // CollectionUtils.subtract(a, b)(A与B的差): {1,2,3}
        // CollectionUtils.subtract(b, a)(B与A的差): {4,6,7}

    }

}
