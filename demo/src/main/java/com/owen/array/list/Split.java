package com.owen.array.list;

import java.util.ArrayList;
import java.util.List;



public class Split {
    
    
    public static void main(String[] args) {
        
        List<String> list=new ArrayList<String>();
        list.add("保护环境");     //向列表中添加数据
        list.add("爱护地球");     //向列表中添加数据
        list.add("从我做起");        //向列表中添加数据
        list.add(1,"从我做起");     //在第1+1个元素的位置添加数据
        //通过循环输出列表中的内容
        for(int i=0;i<list.size();i++){
         System.out.println(i+":"+list.get(i));
        }
      
    }
    
    
    
    
    
//    /**
//     * 获取重复数组信息
//     */
//    public  static void getRepeatData(){
//        
//        int[]  arr1 = {12,13,15,16,17,18,19};
//        int[]  arr2 = {12,13,15,26,17,20,19};
//        
//        //1、合并数组
//        int[] arr3 = Arrays.copyOf(arr1, arr1.length + arr2.length);
//        System.arraycopy(arr2, 0, arr3, arr1.length, arr2.length); 
//        
//        //2.1、冒泡排序获取重复数据
//        List<Integer> arr4 = new ArrayList<Integer>(arr1.length + arr2.length);
//        for(int i=0;i<arr3.length-1;i++){
//            for(int j=arr3.length-1;j> i;j-- ){
//                if(arr3[i] == arr3[j]){
//                    arr4.add(arr3[i]);
//                }
//            }
//        }
//        System.out.println(arr4);
//        
//        
//        //2.2、用map循环效率最高
//        List<Integer> arr6 = new ArrayList<Integer>(arr1.length + arr2.length);
//        HashMap<Integer, Integer> map =  new HashMap<Integer, Integer>(arr1.length + arr2.length);
//        for(int i=0;i<=arr3.length-1;i++){
//            if(map.get(arr3[i]) == null){
//                map.put(arr3[i], 1);
//            }
//            else{
//                map.put(arr3[i], map.get(arr3[i]) + 1);
//                arr6.add(arr3[i]);
//            }
//        }
//        
//        System.out.println(arr6);
//        List<Integer> arr5 = new ArrayList<Integer>(arr1.length + arr2.length);
//        Iterator<Entry<Integer, Integer>> it = map.entrySet().iterator();
//        while(it.hasNext()){
//            Entry<Integer, Integer> entry = it.next();
//            if(entry.getValue() > 1){
//                arr5.add(entry.getKey());
//            }
//        }
//        System.out.println(arr5);
//    }

}
