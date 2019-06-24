package com.owen.string;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

//    public static void main(String[] args) {
//        try{
//            System.out.println(Main.EncoderByMd5("hujumei"));
//
//            Thread.sleep(6000);
//            System.out.println(Main.EncoderByMd5("hujumei"));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        String outTradeNo="1111111_2";
        outTradeNo = outTradeNo.split("_")[0];
        System.out.println(outTradeNo);

        String outTradeNo1="222222";
        outTradeNo = outTradeNo1.split("_")[0];
        System.out.println(outTradeNo1);

    }



    public static void split() {
        String message = "Patch Set 10:" +
                "测试任务: 【CAR】PV测试-SMT-公版" +
                "任务页面: http://test.yunos-inc.com/jobexecutionviewclient?jobExecutionId=288518" +
                "执行设备: 4710b98019379117(串号), ti_ip31(型号), 1.0.1-E-20180122.1804(版本)" +
                "任务结果: 测试失败" +
                "测试报告:" +
                "-  SMT测试(Completed), Pass(14), Fail(1), Skip(0)" +
                "结果: http://test.yunos-inc.com/smt?product=Car&testtasktype=2&device=ti_ip31&version=1.0.1-E-20180122.1804&producttypeid=3&producttag=hostonly&hostname=zhanying.zzy&task_execution_id=628813" +
                "信息: 存在失败用例" +
                "-  车机自动登录(Completed), Pass(0), Fail(0), Skip(0)" +
                "信息: Task finished.";

            if (message.indexOf("-  SMT测试(Completed), Pass(") > 0) {
                String subMessage = message.substring(message.indexOf("-  SMT测试(Completed), Pass("),message.length());
                int pass_s = subMessage.indexOf("Pass(");
                int pass_e = subMessage.indexOf("), Fail(");

                int fail_s = subMessage.indexOf("Fail(");
                int fail_e = subMessage.indexOf("), Skip(");

                System.out.println(subMessage.substring(pass_s + 5, pass_e));
                System.out.println(subMessage.substring(fail_s + 5, fail_e));
        }
    }


    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
