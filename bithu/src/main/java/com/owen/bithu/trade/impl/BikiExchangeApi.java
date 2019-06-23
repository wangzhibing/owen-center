package com.owen.bithu.trade.impl;

import com.owen.bithu.trade.util.MD5Util;

/**
 * Created by Mizlicai on 2019/6/6.
 */
public class BikiExchangeApi {


    /**
     * api路径信息
     */
    public static final String BASE_URL = "";
    public static final String ALL_ORDER = "/open/api/all_order";
    public static final String ALL_TRADE = "/open/api/all_trade";

    /**
     * @param args
     */
    public static final String API_KEY = "";
    public static final String SYMBOL = "";
    public static final Integer PAGESIZE = 20;
    public static final Integer PAGE = 1;


    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取全部委托订单
     */
    public void allOrder() {
        //请求参数：
        Long currentTime = System.currentTimeMillis() / 1000;
        String encodeStr = "api_key" + API_KEY + "symbol" + SYMBOL + "time" + currentTime;
        String mysign = MD5Util.getMD5String(encodeStr);





    }

    /**
     * 获取全部成交记录
     */
    public void allTrade() {

    }

    /**
     * 根据币对取消全部委托单
     */
    public void cancelOrderAll() {

    }
}
