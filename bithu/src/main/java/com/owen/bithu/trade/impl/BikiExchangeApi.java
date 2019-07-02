package com.owen.bithu.trade.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.owen.bithu.trade.api.zgtop.util.HttpUtils;
import com.owen.bithu.trade.util.MD5Util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Mizlicai on 2019/6/6.
 */
public class BikiExchangeApi {


    /**
     * api路径信息
     */
    public static final String url = "https://openapi.biki.com";
    public static final String ALL_ORDER = "/open/api/all_order";
    public static final String ALL_TRADE = "/open/api/all_trade";

    /**
     * @param args
     */
    /*****正式****/
    public static final String API_KEY = "21269ff9073e7674bf3117aebf26eac2";
    public static final String SECRET_KEY = "852ef2e71e38a3e72bff8a66ada3fc73";

    /***暂停的key**/
//    public static final String API_KEY = "4db2be46d40648d4b152629276dc445c";
//    public static final String SECRET_KEY = "d81d108b21d68915df3141a803d3f819";


    public static final String SYMBOL = "";
    public static final Integer PAGESIZE = 20;
    public static final Integer PAGE = 1;


    public static void main(String[] args) {
        BikiExchangeApi api = new BikiExchangeApi();
        api.doGetFinanc();
    }


    /**
     * 获取当前资产
     */
    public void doGetFinanc(){
        Long newOrderCurrentTime = System.currentTimeMillis() / 1000;
        Map params = new TreeMap();
        params.put("api_key", BikiExchangeApi.API_KEY);
        params.put("time", newOrderCurrentTime.toString());
        String linkString = MD5Util.createLinkStringByBiki(params);
        linkString = linkString + SECRET_KEY;
        String newOrdermysign = MD5Util.getMD5String(linkString).toLowerCase();
        params.put("sign", newOrdermysign);
        JSONObject jsonpSellObject = HttpUtils.sendGetRequestForJson(url + "/open/api/user/account", params);
        JSONObject jsonpDataObject = (JSONObject) jsonpSellObject.get("data");
        JSONArray jsonArray = (JSONArray)jsonpDataObject.get("coin_list");
        for(int i=0;i<jsonArray.size();i++){
            JSONObject tempJSONObject = (JSONObject) jsonArray.get(i);
            if(tempJSONObject.get("coin").toString().toLowerCase().equals("usdt")){
                System.out.println(tempJSONObject.get("normal"));
                break;
            }
        }

    }

    /**
     * 撤单
     */
    public void cancelOrder(){

        //1.先进行创建订单
        Long newOrderCurrentTime = System.currentTimeMillis() / 1000;
        Map newOrderMap = new TreeMap();
        newOrderMap.put("side", "BUY");//买单
        newOrderMap.put("type", "1");//限价
        newOrderMap.put("volume", "2");//数量
        newOrderMap.put("price", 0.0160 + "");
        newOrderMap.put("symbol", "vdxusdt");
        newOrderMap.put("api_key", BikiExchangeApi.API_KEY);
        newOrderMap.put("time", newOrderCurrentTime.toString());
        String newOrderlinkString = MD5Util.createLinkStringByBiki(newOrderMap);
        newOrderlinkString = newOrderlinkString + SECRET_KEY;
        String newOrdermysign = MD5Util.getMD5String(newOrderlinkString).toLowerCase();
        newOrderMap.put("sign", newOrdermysign);
        JSONObject newOrderjsonpSellObject = HttpUtils.sendPostRequestForJson(url + "/open/api/create_order", newOrderMap);
        System.out.println("newOrder=" + newOrderjsonpSellObject.toJSONString());
        JSONObject newOrderData = (JSONObject)newOrderjsonpSellObject.get("data");

        //2.查询该订单的状态
        Long orderInfoCurrentTime = System.currentTimeMillis() / 1000;
        Map orderInfpOrderMap = new TreeMap();
        orderInfpOrderMap.put("order_id", newOrderData.get("order_id")+"");
        orderInfpOrderMap.put("symbol", "vdxusdt");
        orderInfpOrderMap.put("api_key", BikiExchangeApi.API_KEY);
        orderInfpOrderMap.put("time", orderInfoCurrentTime.toString());
        String orderInfolinkString = MD5Util.createLinkStringByBiki(orderInfpOrderMap);
        orderInfolinkString = orderInfolinkString + SECRET_KEY;
        String orderinfomysign = MD5Util.getMD5String(orderInfolinkString).toLowerCase();
        orderInfpOrderMap.put("sign", orderinfomysign);
        JSONObject orderInfojsonpSellObject = HttpUtils.sendGetRequestForJson(url + "/open/api/order_info", orderInfpOrderMap);
        System.out.println("OrderInfo=" + orderInfojsonpSellObject.toJSONString());

        //3.撤单
        Long cancelOrderCurrentTime = System.currentTimeMillis() / 1000;
        Map cancelOrderInfpOrderMap = new TreeMap();
        cancelOrderInfpOrderMap.put("order_id", newOrderData.get("order_id")+"");
        cancelOrderInfpOrderMap.put("symbol", "vdxusdt");
        cancelOrderInfpOrderMap.put("api_key", BikiExchangeApi.API_KEY);
        cancelOrderInfpOrderMap.put("time", cancelOrderCurrentTime.toString());
        String cancelOrderlinkString = MD5Util.createLinkStringByBiki(cancelOrderInfpOrderMap);
        cancelOrderlinkString = cancelOrderlinkString + SECRET_KEY;
        String cancelordermysign = MD5Util.getMD5String(cancelOrderlinkString).toLowerCase();
        cancelOrderInfpOrderMap.put("sign", cancelordermysign);
        JSONObject cancelOrderjsonpSellObject = HttpUtils.sendPostRequestForJson(url + "/open/api/cancel_order", cancelOrderInfpOrderMap);
        System.out.println("cancelOrderInfo=" + cancelOrderjsonpSellObject.toJSONString());

    }

    /**
     * 创建订单
     */
    public void newOrder() {
        //请求参数：
        Long currentTime = System.currentTimeMillis() / 1000;
        Map map = new TreeMap();
        map.put("side", "BUY");//买单
        map.put("type", "1");//限价
        map.put("volume", "2");//数量
        map.put("price", 0.0185 + "");
        map.put("symbol", "vdxusdt");
        map.put("api_key", BikiExchangeApi.API_KEY);
        map.put("time", currentTime.toString());
        String linkString = MD5Util.createLinkStringByBiki(map);
        linkString = linkString + SECRET_KEY;
        String mysign = MD5Util.getMD5String(linkString).toLowerCase();
        map.put("sign", mysign);
        JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + "/open/api/create_order", map);
        System.out.println("newOrder=" + jsonpSellObject.toJSONString());
    }

    /**
     * 获取全部委托订单
     */
    public void allOrder() {
        //请求参数：
        Long currentTime = System.currentTimeMillis() / 1000;
        //String encodeStr = "api_key" + API_KEY + "symbol" + SYMBOL + "time" + currentTime;
        Map map = new TreeMap();
        map.put("api_key", BikiExchangeApi.API_KEY);
        map.put("symbol", "vdxusdt");
        map.put("time", currentTime.toString());
        String linkString = MD5Util.createLinkStringByBiki(map);
        linkString = linkString + SECRET_KEY;
        String mysign = MD5Util.getMD5String(linkString).toLowerCase();
        map.put("sign", mysign);
        JSONObject jsonpSellObject = HttpUtils.sendGetRequestForJson(url + "/open/api/v2/all_order", map);
        System.out.println("allOrder=" + jsonpSellObject.toJSONString());
    }


    /**
     * 获取交易所⽀持的所有交易对
     * ok
     */
    public void getAllSymbolsInfo() {
        JSONObject jsonpSellObject = HttpUtils.sendGetRequestForJson(url + "/open/api/common/symbols", null);
        System.out.println("getAllSymbolsInfo=" + jsonpSellObject.toJSONString());
    }


    /**
     * 获取当前行情
     * ok
     */
    public void getTicker() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("symbol", "vdxusdt");
        JSONObject jsonpSellObject = HttpUtils.sendGetRequestForJson(url + "/open/api/get_ticker", params);
        System.out.println("getTicker=" + jsonpSellObject.toJSONString());
    }


    /**
     * 获取深度（150）
     * ok
     */
    public void getMarketDepth() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("symbol", "bikiusdt");
        //step0 step1 step2 精度越来越小。
        params.put("type", "step0");
        JSONObject marketDepthJsonpSellObject = HttpUtils.sendGetRequestForJson(url + "/open/api/market_dept", params);

        JSONObject marketDepthDataJsonpSellObject = (JSONObject) marketDepthJsonpSellObject.get("data");
        JSONObject marketDepthDataTickJsonpSellObject =(JSONObject)  marketDepthDataJsonpSellObject.get("tick");
        JSONArray jsonArrayAsks = (JSONArray) marketDepthDataTickJsonpSellObject.get("asks");
        JSONArray jsonArrayBids = (JSONArray) marketDepthDataTickJsonpSellObject.get("bids");
        System.out.println("getMarketDepth=" + marketDepthJsonpSellObject.toJSONString());
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
