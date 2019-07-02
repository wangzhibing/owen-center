package com.owen.bithu.trade.api.zgtop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.owen.bithu.trade.api.zgtop.util.HttpUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * zg.top APIDemo 示例
 */
public class ZgtopApi {

    public static final String url = "https://zg.top/API/";


    private static final String key = "aa6b8132da65548e4a0017151e6d4945b";
    private static final String secret = "e6d37e7b7bd63856b996b3faf6f58c8e";

    public static void main(String[] args) {

        ZgtopApi zgtopApi = new ZgtopApi();

        zgtopApi.getBalnce();

//        //币对 QOS/USDT
        String symbol = "87";
//
//        //1.获取当前最新行情,无需sign
        //zgtopApi.getPriceBySymbol(symbol);
//
//        //2.获取当前所有最新行情,无需sign
//        zgtopApi.getAllPriceBySymbol();
//
//        //3.获取市场深度
//        zgtopApi.getDepth();
//
//        //4.最近的市场交易
//        zgtopApi.getOrders();
//
//        //5.获取账户信息
//        zgtopApi.getBalnce();
//
//        //6.充值地址
//        zgtopApi.getCoinAddress();

        //7.挂单查询
        //      zgtopApi.getTrandList();

//        //8.查询订单信息
//       zgtopApi.getTrandView();
//
//        //9.取消订单
//        zgtopApi.cancel_trade();
//
//        //10.挂单(buy sell)
//        zgtopApi.tradeBuy();
//
//        //11.获取资产对照信息
//        zgtopApi.getAllCoinsMapper();
    }


    /**
     * 刷单处理：一个账号买，一个账号卖
     */
    public void shuadan(String symbol) {

        //1.第一步：获取该币对最新的买一价、卖一价
        getPriceBySymbol(symbol);


    }

    /**
     * 1. 获取当前最新行情
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     */
    public void getPriceBySymbol(String symbol) {
        try {
            String method = "/api/v1/ticker";
            Map map = new HashMap();
            map.put("symbol", symbol);

            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
            dataJSONObject.get("last");
            String last = jsonpObject.toString();
            System.out.println("【获取当前最新行情】:" + last);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. 获取当前所有最新行情
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     */
    public void getAllPriceBySymbol() {
        try {
            String method = "/api/v1/tickers";
            Map map = new HashMap();
            map.put("symbol", "35");
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            String last = jsonpObject.toString();
            System.out.println("【获取当前所有最新行情】:" + last);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3. 市场深度 （ 返回所有的市场深度，此回应的数据量会较大，所以请勿频繁调用）
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     * merge - 小数位，默认为0(0,1,2,3,4)
     */
    public void getDepth() {
        try {
            while (true) {
                String method = "/api/v1/depth";
                Map map = new HashMap();
                map.put("symbol", "87");
                map.put("merge", "4");
                JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
                System.out.println("getDepth:" + JSON.toJSONString(jsonpObject));
                //asks - 委买单[价格, 委单量]，价格从高到低排序
                //bids - 委卖单[价格, 委单量]，价格从低到高排序

                JSONArray buys = JSONObject.parseArray(JSONObject.parseObject(jsonpObject.getString("data")).getString("asks"));
                JSONArray sells = JSONObject.parseArray(JSONObject.parseObject(jsonpObject.getString("data")).getString("bids"));
                System.out.println("时间" + System.currentTimeMillis() + ",buys.size():" + buys.size() + ",sells.size():" + sells.size());
                System.out.println("时间" + System.currentTimeMillis() + ",buys:" + buys.toJSONString() + ",sells:" + sells.toJSONString());
                if (buys == null || buys.size() < 35) {
                } else {
                    JSONArray singleJsonArray = (JSONArray) buys.get(34);
                    BigDecimal singleBuyPrice = new BigDecimal(singleJsonArray.get(0).toString());
                }

                if (sells == null || sells.size() < 35) {
                } else {

                    JSONArray singleJsonArray = (JSONArray) sells.get(34);
                    BigDecimal singleSellPrice = new BigDecimal(singleJsonArray.get(0).toString());
                }


            }
//            Map<String, JSONArray> resultMap = new HashMap();
//            resultMap.put("buys", buys);
//            resultMap.put("sells", sells);
//            System.out.println("【市场深度】:" + resultMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 4. 最近的市场交易 （ 返回100个最近的市场交易，按时间倒序排列，此回应的数据量会较大，所以请勿频繁调用）
     * <p>
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     * size - 记录数，默认100，最大100
     */

    public void getOrders() {
        try {
            String method = "/api/v1/orders";
            Map map = new HashMap();
            map.put("symbol", "87");
            map.put("type", "sell");
            map.put("size", "20");
            JSONObject jsonObject = HttpUtils.sendGetRequestForJson(url + method, map);
            System.out.println("【最近的市场交易】:" + jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 5. 账户信息 （ 列举您的帐户资产信息）
     * <p>
     * 参数
     * 无
     */

    public void getBalnce() {
        try {
            String method = "/api/v1/balances";
            Map map = new TreeMap<>();
            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONArray jsonArrayData = (JSONArray) jsonpObject.get("data");
            for(int i=0;i<jsonArrayData.size();i++){
                JSONObject tempJsonObject = (JSONObject)jsonArrayData.get(i);
                if(tempJsonObject.get("symbol").toString().equals("87")){
                    System.out.println(tempJsonObject.get("balance"));
                }
            }
            System.out.println("【账户信息】:" + jsonpObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 6. 充值地址
     * <p>
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     */

    public void getCoinAddress() {
        try {

            String method = "/api/v1/coin_address";

            Map map = new TreeMap();
            map.put("symbol", "35");

            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");

            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            System.out.println("【充值地址】:" + jsonpObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 7. 挂单查询 （ 您指定时间后的挂单，可以根据类型查询，比如查看正在挂单和全部挂单）
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     * since - Unix时间戳(s)， 默认0
     * type - 挂单 类型[1: 正在挂单, 0: 所有挂单]
     */

    public void getTrandList() {
        try {

            String method = "/api/v1/trade_list";

            Map map = new TreeMap();
            map.put("symbol", "87");
            map.put("since", "0");
            map.put("type", "0");

            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");

            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);

            System.out.println("【挂单查询】:" + jsonpObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 8. 查询订单信息
     * <p>
     * 参数
     * id - 挂单ID
     */

    public void getTrandView(String tradeId) {
        try {

            String method = "/api/v1/trade_view";

            Map map = new TreeMap();
            map.put("id", tradeId);

            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");

            JSONObject jsonObject = HttpUtils.sendGetRequestForJson(url + method, map);
            System.out.println("【查询订单信息】:" + jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 9.取消订单
     * 参数
     * id - 挂单ID
     */

    public void cancel_trade() {
        try {

            String method = "/api/v1/cancel_trade";

            Map<String, Object> map = new TreeMap<>();
            map.put("id", "1");

            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");

            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);

            System.out.println(jsonpObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 10. 挂单
     * <p>
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     * amount - 下单数量
     * price - 下单价格
     * type - "buy" or "sell"
     */

    public void tradeBuy() {
        try {

            String method = "/api/v1/trade";

            //1.
            Map<String, Object> map = new TreeMap<>();
            map.put("symbol", "87");
            map.put("amount", "3000");
            map.put("price", new BigDecimal("0.002340"));
            map.put("type", "buy");

            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            System.out.println(jsonpObject.toString());

            //2.
            Map<String, Object> map2 = new TreeMap<>();
            map2.put("symbol", "87");
            map2.put("amount", "3000");
            map2.put("price", new BigDecimal("0.002335"));
            map2.put("type", "buy");

            map2.put("key", key);
            map2.put("secret", secret);
            String sign2 = HttpUtils.getSignature(map2);
            map2.put("sign", sign2);
            map2.remove("secret");
            JSONObject jsonpObject2 = HttpUtils.sendGetRequestForJson(url + method, map2);
            System.out.println(jsonpObject2.toString());

            //3.
            Map<String, Object> map3 = new TreeMap<>();
            map3.put("symbol", "87");
            map3.put("amount", "3000");
            map3.put("price", new BigDecimal("0.002330"));
            map3.put("type", "buy");

            map3.put("key", key);
            map3.put("secret", secret);
            String sign3 = HttpUtils.getSignature(map3);
            map3.put("sign", sign3);
            map3.remove("secret");
            JSONObject jsonpObject3 = HttpUtils.sendGetRequestForJson(url + method, map3);
            System.out.println(jsonpObject3.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 10. 挂单
     * <p>
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     * amount - 下单数量
     * price - 下单价格
     * type - "buy" or "sell"
     */

    public void tradeSell() {
        try {

            String method = "/api/v1/trade";

            Map<String, Object> map = new TreeMap<>();
            map.put("symbol", "87");
            map.put("amount", "1");
            map.put("price", "1");
            map.put("type", "buy");

            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");

            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);

            System.out.println(jsonpObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 11.获取资产对照信息
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     */
    public void getAllCoinsMapper() {
        try {
            String method = "/api/v1/coins";
            JSONObject jsonObject = HttpUtils.sendGetRequestForJson(url + method, null);
            String last = jsonObject.toString();
            System.out.println("【获取资产对照信息】:" + last);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
