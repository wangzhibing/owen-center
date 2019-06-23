package com.owen.bithu.trade.api.zgtop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.owen.bithu.trade.api.zgtop.util.HttpUtils;
import com.owen.bithu.trade.api.zgtop.util.LimitQueue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.StreamSupport;

import static com.owen.bithu.trade.api.zgtop.ZgtopConstant.errorMapping;

/**
 * zg.top APIDemo 示例
 */
public class ZgtopApi2 {

    //public static final String url = "https://zg.top/API/";
    public static final String url = "https://zgtop.io/API/";
    private static final String key = "aa6b8132da65548e4a0017151e6d4945b";
    private static final String secret = "e6d37e7b7bd63856b996b3faf6f58c8e";


    //4.变量4：为单位价格
    public static final BigDecimal unitPrice = new BigDecimal("0.000001");
    public static final String symbol = "87";

    //查询当前交易信息
    public static final String method = "/api/v1/ticker";
    //挂单路径
    public static final String methodTrade = "api/v1/trade";

    public static void main(String[] args) throws Exception {
        //ZgtopApi zgtopApi = new ZgtopApi();
        ZgtopApi2 zgtopApi2 = new ZgtopApi2();

        //zgtopApi2.doTradeAndcancel();

        //zgtopApi2.cancelTrade();

        //zgtopApi2.doDeep();

        //zgtopApi2.doOpBuyLimit();

        //zgtopApi2.autoTrade4();


        //**********************核心方法**********************
        //1.深度
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    zgtopApi2.doDeep();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();

        //2.自动成交
        zgtopApi2.autoTrade4();
        //*********************************************

    }


    /**
     * 自动成交
     */
    public void autoTrade() throws Exception {

        //设置初始值
        int p = 0;

        long splitTime = 5000l;

        //有界队列20
        LimitQueue<Integer> limitQueueX = new LimitQueue<>(20);


        while (true) {
            //设置每次获取随机数X,范围是【0~100】
            Random randomX = new Random();
            limitQueueX.offer(randomX.nextInt(100));

            //求limitQueueX平均值
            int sunmX = StreamSupport.stream(limitQueueX.spliterator(), false).reduce(0, (acc, element) -> acc + element);

            //得出平均值
            Integer a = sunmX % limitQueueX.size();

            //对平均值判断处理
            if (a > 50) {

            }

            Thread.sleep(splitTime);

        }


        //List<Integer>


    }


    /**
     * 方案2
     * 自动成交
     */
    public void autoTrade2() throws Exception {

        //0.symbol
        ///String symbol = "87";

        //1.变量1：设置初始值, 当前价格初始值
        BigDecimal currentPrice = BigDecimal.ZERO;

        //2.变量2：成交频率时间间隔 区间 【1~5】
        long splitTimeMin = 1;
        long splitTimeMax = 5;

        //3.变量3：成交数量，几手，最后都是偶数，后续需要运算。
        int tradeAccount = 0;

        //4.变量4：为单位价格
        //BigDecimal unitPrice = new BigDecimal("0.000001");

        //5.变量5：随机买卖数量【1000~3000】
        int vRangeInitValue = 1000;
        int vRangeMaxValue = 2000;

        //6.变量6：随机数，判断阈值
        int xRange = 100;


        Random randomX = new Random();


        while (true) {
            //设置每次获取随机数X,范围是【0~100】
            int x = randomX.nextInt(xRange);

            //获取当前价格
            String method = "/api/v1/ticker";
            Map map = new HashMap();
            map.put("symbol", symbol);
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
            currentPrice = new BigDecimal(dataJSONObject.get("last").toString());


            //对平均值判断处理
            int sellCount = 0;
            int buyCount = 0;
            BigDecimal tradePrice = BigDecimal.ZERO;
            if (x > 50) {
                //当X>50自动挂价格（C+i） 数量 V手 卖单， 挂价格为(C+i) V/2的买单
                sellCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                buyCount = sellCount / 2;
                tradePrice = currentPrice.add(unitPrice);
            } else {
                //当X<50  自动挂 价格（c-i） 数量为V手买单，价格为（c-i） 数量为v/2额卖单
                buyCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                sellCount = buyCount / 2;
                tradePrice = currentPrice.subtract(unitPrice);
            }

            //3.挂单：
            String methodTrade = "api/v1/trade";
            //3.1.卖一挂单信息
            Map<String, Object> mapSellParmas = new TreeMap<>();
            mapSellParmas.put("symbol", symbol);
            mapSellParmas.put("amount", sellCount);
            mapSellParmas.put("price", tradePrice);
            mapSellParmas.put("type", "sell");
            mapSellParmas.put("key", key);
            mapSellParmas.put("secret", secret);
            String sellSign = HttpUtils.getSignature(mapSellParmas);
            mapSellParmas.put("sign", sellSign);
            mapSellParmas.remove("secret");

            //3.2.买一挂单信息
            Map<String, Object> mapBuyParmas = new TreeMap<>();
            mapBuyParmas.put("symbol", symbol);
            mapBuyParmas.put("amount", buyCount);
            mapBuyParmas.put("price", tradePrice);
            mapBuyParmas.put("type", "buy");
            mapBuyParmas.put("key", key);
            mapBuyParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapBuyParmas);
            mapBuyParmas.put("sign", buySign);
            mapBuyParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
            System.out.println("【jsonpSellObject】:" + JSON.toJSONString(jsonpSellObject));
            JSONObject jsonBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
            System.out.println("【jsonBuyObject】:" + JSON.toJSONString(jsonBuyObject));

            //每次做完一笔，需沉睡splitTime毫秒
            System.out.println("******************");
            Random ran = new Random();
            ran.nextInt(5);
            Thread.sleep((1 + ran.nextInt(5)) * 1000);
        }


        //List<Integer>


    }


    /**
     * 方案3
     * 自动成交
     */
    public void autoTrade3() throws Exception {

        //0.symbol
        //String symbol = "87";

        //1.变量1：设置初始值, 当前价格初始值
        //BigDecimal currentPrice = BigDecimal.ZERO;

        //2.变量2：成交频率时间间隔 区间 【1~10】
        long splitTimeMin = 1;
        long splitTimeMax = 5;

        //3.变量3：成交数量，几手，最后都是偶数，后续需要运算。
        int tradeAccount = 0;

        //4.变量4：为单位价格
        //BigDecimal unitPrice = new BigDecimal("0.000001");

        //5.变量5：随机买卖数量【1000~3000】
        int vRangeInitValue = 400;
        int vRangeMaxValue = 200;

        //6.变量6：随机数，判断阈值
        int xRange = 100;


        Random randomX = new Random();


        while (true) {
            //设置每次获取随机数X,范围是【0~100】
            int x = randomX.nextInt(xRange);

            //获取当前价格
            String method = "/api/v1/ticker";
            Map map = new HashMap();
            map.put("symbol", symbol);
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
            BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
            BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
            BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());


            //对平均值判断处理
            int sellCount = 0;
            int buyCount = 0;
            BigDecimal tradePrice = BigDecimal.ZERO;
            if (x > 50) {
                //当X>50自动挂价格（SELL1-i） 数量 V手 卖单，挂价格为(SELL1-i) V/2的买单
                sellCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                buyCount = sellCount / 2;
                tradePrice = sellPrice.subtract(unitPrice);
            } else {
                //当X<50  自动挂 价格（BUY1+i） 数量为V手买单，价格为（BUY1+i） 数量为v/2额卖单
                buyCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                sellCount = buyCount / 2;
                tradePrice = buyPrice.add(unitPrice);
            }

            //3.挂单：
            String methodTrade = "api/v1/trade";
            //3.1.卖一挂单信息
            Map<String, Object> mapSellParmas = new TreeMap<>();
            mapSellParmas.put("symbol", symbol);
            mapSellParmas.put("amount", sellCount);
            mapSellParmas.put("price", tradePrice);
            mapSellParmas.put("type", "sell");
            mapSellParmas.put("key", key);
            mapSellParmas.put("secret", secret);
            String sellSign = HttpUtils.getSignature(mapSellParmas);
            mapSellParmas.put("sign", sellSign);
            mapSellParmas.remove("secret");

            //3.2.买一挂单信息
            Map<String, Object> mapBuyParmas = new TreeMap<>();
            mapBuyParmas.put("symbol", symbol);
            mapBuyParmas.put("amount", buyCount);
            mapBuyParmas.put("price", tradePrice);
            mapBuyParmas.put("type", "buy");
            mapBuyParmas.put("key", key);
            mapBuyParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapBuyParmas);
            mapBuyParmas.put("sign", buySign);
            mapBuyParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
            System.out.println("【jsonpSellObject】:" + JSON.toJSONString(jsonpSellObject));
            JSONObject jsonBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
            System.out.println("【jsonBuyObject】:" + JSON.toJSONString(jsonBuyObject));

            //每次做完一笔，需沉睡splitTime毫秒
            System.out.println("******************");
            Random ran = new Random();
            Thread.sleep((1 + ran.nextInt(10)) * 1000);
        }
    }

    /**
     * 方案4
     * 自动成交
     */
    public void autoTrade4() throws Exception {

        //0.symbol
        //String symbol = "87";

        //1.变量1：设置初始值, 当前价格初始值
        //BigDecimal currentPrice = BigDecimal.ZERO;

        //2.变量2：成交频率时间间隔 区间 【1~10】
        long splitTimeMin = 1;
        long splitTimeMax = 5;

        //3.变量3：成交数量，几手，最后都是偶数，后续需要运算。
        int tradeAccount = 0;

        //4.变量4：为单位价格
        //BigDecimal unitPrice = new BigDecimal("0.000001");

        //5.变量5：随机买卖数量【1000~3000】
        int vRangeInitValue = 400;
        int vRangeMaxValue = 200;

        //6.变量6：随机数，判断阈值
        int xRange = 100;


        Random randomX = new Random();


        while (true) {
            System.out.println("自动成交开始******************");
            //设置每次获取随机数X,范围是【0~100】
            int x = randomX.nextInt(xRange);

            //获取当前价格
            String method = "/api/v1/ticker";
            Map map = new HashMap();
            map.put("symbol", symbol);
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
            BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
            BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
            BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());


            //对平均值判断处理
            int sellCount = 0;
            int buyCount = 0;
            BigDecimal tradePrice = BigDecimal.ZERO;
            if (x > 50) {
                //当X>50自动挂价格（SELL1-i） 数量 V手 卖单，挂价格为(SELL1-i) V/2的买单
                sellCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                buyCount = sellCount / 2;
                tradePrice = sellPrice.subtract(unitPrice);
                System.out.println("X>50........");

            } else {
                //当X<50  自动挂 价格（SELL1） 数量为V手买单，价格为（SELL1） 数量为v/2额卖单
                buyCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                sellCount = buyCount / 2;
                tradePrice = sellPrice;
                System.out.println("X<50........");
            }

            //3.挂单：
            String methodTrade = "api/v1/trade";
            //3.1.卖一挂单信息
            Map<String, Object> mapSellParmas = new TreeMap<>();
            mapSellParmas.put("symbol", symbol);
            mapSellParmas.put("amount", sellCount);
            mapSellParmas.put("price", tradePrice);
            mapSellParmas.put("type", "sell");
            mapSellParmas.put("key", key);
            mapSellParmas.put("secret", secret);
            String sellSign = HttpUtils.getSignature(mapSellParmas);
            mapSellParmas.put("sign", sellSign);
            mapSellParmas.remove("secret");

            //3.2.买一挂单信息
            Map<String, Object> mapBuyParmas = new TreeMap<>();
            mapBuyParmas.put("symbol", symbol);
            mapBuyParmas.put("amount", buyCount);
            mapBuyParmas.put("price", tradePrice);
            mapBuyParmas.put("type", "buy");
            mapBuyParmas.put("key", key);
            mapBuyParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapBuyParmas);
            mapBuyParmas.put("sign", buySign);
            mapBuyParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
            System.out.println("【卖单jsonpSellObject】:" + JSON.toJSONString(jsonpSellObject));
            JSONObject jsonBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
            System.out.println("【买单jsonBuyObject】:" + JSON.toJSONString(jsonBuyObject));

            //每次做完一笔，需沉睡splitTime毫秒
            System.out.println("自动成交解散******************");
            Random ran = new Random();
            Thread.sleep((1 + ran.nextInt(10)) * 1000);
        }
    }


    /**
     * 深度处理
     */
    public void doDeep() throws Exception {

        //1.
        doOpBuyLimit();

        //2.
        doOpSellLimit();
        while (true) {
            System.out.println("深度处理开始*******");
            //3获取当前价格
            Map map = new HashMap();
            map.put("symbol", symbol);
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
            BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
            BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
            BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());

            //预测价格
            BigDecimal expectBuy100Price = buyPrice.subtract(unitPrice.multiply(new BigDecimal(35)));
            BigDecimal expectSell100Price = sellPrice.add(unitPrice.multiply(new BigDecimal(35)));

            //获取时时实际的买100，卖100的价格。
            String method = "/api/v1/depth";
            Map mapDeepth = new HashMap();
            mapDeepth.put("symbol", symbol);
            mapDeepth.put("merge", "4");
            JSONObject jsonDeepthObject = HttpUtils.sendGetRequestForJson(url + method, mapDeepth);
            System.out.println("getDepth:" + JSON.toJSONString(jsonDeepthObject));
            //asks - 委买单[价格, 委单量]，价格从高到低排序
            //bids - 委卖单[价格, 委单量]，价格从低到高排序
            JSONArray buys = JSONObject.parseArray(JSONObject.parseObject(jsonDeepthObject.getString("data")).getString("asks"));
            JSONArray sells = JSONObject.parseArray(JSONObject.parseObject(jsonDeepthObject.getString("data")).getString("bids"));

            System.out.println("预期委托买价：" + expectBuy100Price);
            System.out.println("预期委托卖价：" + expectSell100Price);
            System.out.println("最新的委托买单信息：" + buys.toJSONString());
            System.out.println("最新的委托卖单信息：" + sells.toJSONString());

            System.out.println("buys.size()=" + buys.size() + ",sells.size()=" + sells.size());
            if (buys == null || buys.size() < 35) {
                System.out.println("buys买单长度不够，进入深度。。。");
                int diff = 35 - buys.size();
                JSONArray lastSingleBuyJsonArray = (JSONArray) buys.get(buys.size() - 1);
                BigDecimal diffPrice = new BigDecimal(lastSingleBuyJsonArray.get(0).toString());
                doOpBuyLimit(diff, diffPrice);
            } else {
                JSONArray singleJsonArray = (JSONArray) buys.get(35);
                BigDecimal singleBuyPrice = new BigDecimal(singleJsonArray.get(0).toString());
                BigDecimal diffUnit = singleBuyPrice.subtract(expectBuy100Price);
                System.out.println("buy>=35,但是价格不相等，相差：" + diffUnit);
                if (diffUnit.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal divideNum = diffUnit.divide(unitPrice);
                    System.out.println("buy>=35,但是价格不相等，divideNum：" + divideNum);
                    if (divideNum.compareTo(new BigDecimal(20)) > 0) {
                        doOpBuyLimit();
                    }
                }
            }

            if (sells == null || sells.size() < 35) {
                System.out.println("sells卖单长度不够，进入深度。。。");
                int diff = 35 - sells.size();
                JSONArray lastSingleSellJsonArray = (JSONArray) sells.get(buys.size() - 1);
                BigDecimal diffPrice = new BigDecimal(lastSingleSellJsonArray.get(0).toString());
                doOpSellLimit(diff, diffPrice);
            } else {
                JSONArray singleJsonArray = (JSONArray) sells.get(35);
                BigDecimal singleSellPrice = new BigDecimal(singleJsonArray.get(0).toString());
                BigDecimal diffUnit = singleSellPrice.subtract(expectSell100Price);
                System.out.println("sell>=35,但是价格不相等，相差：" + diffUnit);
                if (diffUnit.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal divideNum = diffUnit.divide(unitPrice);
                    System.out.println("sell>=35,但是价格不相等，divideNum：" + divideNum);
                    if (divideNum.compareTo(new BigDecimal(20)) > 0) {
                        doOpSellLimit();
                    }
                }
            }

            System.out.println("深度处理结束*******");
            Thread.sleep(10000l);
        }

    }


    public void doOpBuyLimit(int diff, BigDecimal price) {
        System.out.println("进入doOpBuyLimit，diff:"+diff+",price:"+price);
        BigDecimal buyTradePrice = BigDecimal.ZERO;
        int vRangeInitValue = 400;
        int vRangeMaxValue = 200;
        Random randomX = new Random();
        for (int i = 1; i <= diff; i++) {
            buyTradePrice = price.subtract(unitPrice.multiply(new BigDecimal(i)));
            Map<String, Object> mapBuyParmas = new TreeMap<>();
            mapBuyParmas.put("symbol", symbol);
            mapBuyParmas.put("amount", vRangeInitValue + randomX.nextInt(vRangeMaxValue));
            mapBuyParmas.put("price", buyTradePrice);
            mapBuyParmas.put("type", "buy");
            mapBuyParmas.put("key", key);
            mapBuyParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapBuyParmas);
            mapBuyParmas.put("sign", buySign);
            mapBuyParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
            System.out.println("判断差" + i + ",深度连续挂单【买】第" + i + "单,返回信息:" + jsonpBuyObject);
        }
    }

    /**
     * 挂买单
     */
    public void doOpBuyLimit() {
        System.out.println("买挂单开始*******");
        Long sTime = System.currentTimeMillis();
        //1.获取当前价格
        Map map = new HashMap();
        map.put("symbol", symbol);
        JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
        JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
        BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
        BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
        BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());

        //2.刷单
        int buyCount = 35;

        //3.变量5：随机买卖数量【1000~3000】
        int vRangeInitValue = 400;
        int vRangeMaxValue = 200;
        Random randomX = new Random();

        BigDecimal buyTradePrice = BigDecimal.ZERO;
        for (int i = 1; i <= buyCount; i++) {
            buyTradePrice = buyPrice.subtract(unitPrice.multiply(new BigDecimal(i)));
            Map<String, Object> mapBuyParmas = new TreeMap<>();
            mapBuyParmas.put("symbol", symbol);
            mapBuyParmas.put("amount", vRangeInitValue + randomX.nextInt(vRangeMaxValue));
            mapBuyParmas.put("price", buyTradePrice);
            mapBuyParmas.put("type", "buy");
            mapBuyParmas.put("key", key);
            mapBuyParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapBuyParmas);
            mapBuyParmas.put("sign", buySign);
            mapBuyParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
            System.out.println("深度连续挂单【买】第" + i + "单,返回信息:" + jsonpBuyObject);
//            String tradeId = jsonpBuyObject.get("id").toString();
//            JSONObject tradeInfoJSONObject = this.getTrandView(tradeId);
//            System.out.println("深度连续挂单【买】第"+i+"单,订单信息结果:" + tradeInfoJSONObject);
        }
        System.out.println("深度连续挂单【买】100单,耗时:" + (System.currentTimeMillis() - sTime));
        System.out.println("买挂单结束*******");
    }


    public void doOpSellLimit(int diff, BigDecimal price) {
        System.out.println("进入doOpSellLimit，diff:"+diff+",price:"+price);
        BigDecimal sellTradePrice = BigDecimal.ZERO;
        int vRangeInitValue = 400;
        int vRangeMaxValue = 200;
        Random randomX = new Random();
        for (int i = 1; i <= diff; i++) {
            sellTradePrice = price.add(unitPrice.multiply(new BigDecimal(i)));
            Map<String, Object> mapSellParmas = new TreeMap<>();
            mapSellParmas.put("symbol", symbol);
            mapSellParmas.put("amount", vRangeInitValue + randomX.nextInt(vRangeMaxValue));
            mapSellParmas.put("price", sellTradePrice);
            mapSellParmas.put("type", "sell");
            mapSellParmas.put("key", key);
            mapSellParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapSellParmas);
            mapSellParmas.put("sign", buySign);
            mapSellParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
            System.out.println("判断差" + i + ",深度连续挂单【卖】第" + i + "单,返回信息:" + jsonpSellObject);
        }
    }


    /**
     * 挂卖单
     */
    public void doOpSellLimit() {
        System.out.println("卖挂单开始*******");
        Long sTime = System.currentTimeMillis();
        //1.获取当前价格

        Map map = new HashMap();
        map.put("symbol", symbol);
        JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
        JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
        BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
        BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
        BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());

        //2.刷单
        int sellCount = 35;

        //3.变量5：随机买卖数量【1000~3000】
        int vRangeInitValue = 400;
        int vRangeMaxValue = 200;
        Random randomX = new Random();

        BigDecimal sellTradePrice = BigDecimal.ZERO;
        for (int i = 1; i <= sellCount; i++) {
            sellTradePrice = sellPrice.add(unitPrice.multiply(new BigDecimal(i)));
            Map<String, Object> mapSellParmas = new TreeMap<>();
            mapSellParmas.put("symbol", symbol);
            mapSellParmas.put("amount", vRangeInitValue + randomX.nextInt(vRangeMaxValue));
            mapSellParmas.put("price", sellTradePrice);
            mapSellParmas.put("type", "sell");
            mapSellParmas.put("key", key);
            mapSellParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapSellParmas);
            mapSellParmas.put("sign", buySign);
            mapSellParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
            System.out.println("深度连续挂单【卖】第" + i + "单,返回信息:" + jsonpSellObject);
            //String tradeId = jsonpSellObject.get("id").toString();
            //JSONObject tradeInfoJSONObject = this.getTrandView(tradeId);
            //System.out.println("深度连续挂单【卖】第"+i+"单,订单信息结果:" + tradeInfoJSONObject);
        }

        System.out.println("深度连续挂单【卖】100单,耗时:" + (System.currentTimeMillis() - sTime));
        System.out.println("卖挂单结束*******");
    }


    /**
     * 刷单处理：一个账号买，一个账号卖
     */
    public void shuadan(String symbol) {
        System.out.println("【冲量】开始");
        Map<String, BigDecimal> resMap = new HashMap<>();
        try {
            //1.获取买一价、卖一价
            String methodTicker = "/api/v1/ticker";
            Map map = new HashMap();
            map.put("symbol", symbol);
            JSONObject jsonObject = HttpUtils.sendGetRequestForJson(url + methodTicker, map);
            String code = jsonObject.get("code").toString();
            if (code.equals(ZgtopConstant.HTTP_SUCCESS_CODE)) {
                JSONObject jsonObjectData = (JSONObject) jsonObject.get("data");
                BigDecimal buyOne = new BigDecimal(jsonObjectData.get("buy").toString());
                BigDecimal sellOne = new BigDecimal(jsonObjectData.get("sell").toString());
                System.out.println("【买一价：" + buyOne + "，卖一价" + sellOne + "】");

                //2.计算盘口差价
                //正常情况：卖一价 > 买一价
                BigDecimal activePrice = this.getActivePrice(buyOne, sellOne);
                System.out.println("【实际刷单价格】:" + activePrice);

                //3.挂单：
                String methodTrade = "api/v1/trade";
                //3.1.卖一挂单信息
                Map<String, Object> mapSellParmas = new TreeMap<>();
                mapSellParmas.put("symbol", symbol);
                mapSellParmas.put("amount", "200");
                mapSellParmas.put("price", activePrice);
                mapSellParmas.put("type", "sell");
                mapSellParmas.put("key", key);
                mapSellParmas.put("secret", secret);
                String sellSign = HttpUtils.getSignature(mapSellParmas);
                mapSellParmas.put("sign", sellSign);
                mapSellParmas.remove("secret");

                //3.2.买一挂单信息
                Map<String, Object> mapBuyParmas = new TreeMap<>();
                mapBuyParmas.put("symbol", symbol);
                mapBuyParmas.put("amount", "200");
                mapBuyParmas.put("price", activePrice);
                mapBuyParmas.put("type", "buy");
                mapBuyParmas.put("key", key);
                mapBuyParmas.put("secret", secret);
                String buySign = HttpUtils.getSignature(mapBuyParmas);
                mapBuyParmas.put("sign", buySign);
                mapBuyParmas.remove("secret");

                //3.3.开始挂单
                JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
                System.out.println("【jsonpSellObject】:" + JSON.toJSONString(jsonpSellObject));
                JSONObject jsonBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
                System.out.println("【jsonBuyObject】:" + JSON.toJSONString(jsonBuyObject));
            } else {
                System.out.println("【获取当前最新行情】失败，code=" + code + ",消息=" + errorMapping.get(code));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("【冲量】结束");
    }

    /**
     * 1. 获取当前最新行情
     * 参数
     * symbol - 1,2...( 区块链资产对照表)
     * {"code": 200,"data": {"high": 0.0,"vol": 0.0,"last": 0.999,"low": 0.0,"buy": 0.0,"sell": 0.0}}
     */
    public Map<String, BigDecimal> getPriceBySymbol(String symbol) {
        Map<String, BigDecimal> resMap = new HashMap<>();
        try {
            String method = "/api/v1/ticker";
            Map map = new HashMap();
            map.put("symbol", symbol);
            JSONObject jsonObject = HttpUtils.sendGetRequestForJson(url + method, map);
            String code = jsonObject.get("code").toString();
            if (code.equals(ZgtopConstant.HTTP_SUCCESS_CODE)) {
                JSONObject jsonObjectData = (JSONObject) jsonObject.get("data");
                BigDecimal buyOne = new BigDecimal(jsonObjectData.get("buy").toString());
                BigDecimal sellOne = new BigDecimal(jsonObjectData.get("sell").toString());
                System.out.println("【买一价：" + buyOne + "，卖一价" + sellOne + "】");
                resMap.put("buy", buyOne);
                resMap.put("sell", sellOne);
            } else {
                System.out.println("【获取当前最新行情】失败，code=" + code + ",消息=" + errorMapping.get(code));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resMap;
    }

    /**
     * 根据策略返回实际价格
     *
     * @param buyOne
     * @param sellOne
     * @return
     */
    private BigDecimal getActivePrice(BigDecimal buyOne, BigDecimal sellOne) {

        //1. strategy 1:random
        buyOne.add((sellOne.subtract(buyOne)).divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP));

        BigDecimal diff = sellOne.subtract(buyOne);

        //2.种子为10(可配置，随机取数按比例)
        float randomIndex = new Random().nextInt(10);
        if (randomIndex == 0) {
            randomIndex = 1;
        }

        float rate = randomIndex / 10;
        return buyOne.add(diff.multiply(new BigDecimal(rate)), new MathContext(4, RoundingMode.HALF_UP));
    }


    /**
     * 撤单
     * 先查询挂单信息，然后在循环撤单
     */
    public void doTradeAndcancel() {

        try {

            //0.symbol
            //String symbol = "87";

            //1.变量1：设置初始值, 当前价格初始值
            //BigDecimal currentPrice = BigDecimal.ZERO;

            //2.变量2：成交频率时间间隔 区间 【1~10】
            long splitTimeMin = 1;
            long splitTimeMax = 5;

            //3.变量3：成交数量，几手，最后都是偶数，后续需要运算。
            int tradeAccount = 0;

            //4.变量4：为单位价格
            //BigDecimal unitPrice = new BigDecimal("0.000001");

            //5.变量5：随机买卖数量【1000~3000】
            int vRangeInitValue = 400;
            int vRangeMaxValue = 200;

            //6.变量6：随机数，判断阈值
            int xRange = 100;


            Random randomX = new Random();


            System.out.println("自动成交开始******************");
            //设置每次获取随机数X,范围是【0~100】
            int x = randomX.nextInt(xRange);

            //获取当前价格
            String method = "/api/v1/ticker";
            Map map = new HashMap();
            map.put("symbol", symbol);
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
            BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
            BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
            BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());


            //对平均值判断处理
            int sellCount = 0;
            int buyCount = 0;
            BigDecimal tradePrice = BigDecimal.ZERO;
            if (x > 50) {
                //当X>50自动挂价格（SELL1-i） 数量 V手 卖单，挂价格为(SELL1-i) V/2的买单
                sellCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                buyCount = sellCount / 2;
                tradePrice = sellPrice.subtract(unitPrice);
            } else {
                //当X<50  自动挂 价格（SELL1） 数量为V手买单，价格为（SELL1） 数量为v/2额卖单
                buyCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
                sellCount = buyCount / 2;
                tradePrice = sellPrice;
            }

            //3.挂单：
            //3.2.买一挂单信息
            Map<String, Object> mapBuyParmas = new TreeMap<>();
            mapBuyParmas.put("symbol", symbol);
            mapBuyParmas.put("amount", buyCount);
            mapBuyParmas.put("price", tradePrice);
            mapBuyParmas.put("type", "buy");
            mapBuyParmas.put("key", key);
            mapBuyParmas.put("secret", secret);
            String buySign = HttpUtils.getSignature(mapBuyParmas);
            mapBuyParmas.put("sign", buySign);
            mapBuyParmas.remove("secret");

            //3.3.开始挂单
            JSONObject jsonpBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
            System.out.println("【jsonpSellObject】:" + JSON.toJSONString(jsonpBuyObject));


            String tradeId = jsonpBuyObject.get("id").toString();
            System.out.println("&&&&&&&&&撤单前状态：");
            this.getTrandView(tradeId);
            //撤单

            String method2 = "/api/v1/cancel_trade";

            Map<String, Object> canelMap = new TreeMap<>();
            canelMap.put("id", tradeId);

            canelMap.put("key", key);
            canelMap.put("secret", secret);
            String signCancel = HttpUtils.getSignature(canelMap);
            canelMap.put("sign", signCancel);
            canelMap.remove("secret");

            JSONObject jsonpObjectCancel = HttpUtils.sendPostRequestForJson(url + method2, canelMap);
            System.out.println(jsonpObjectCancel.toString());
            System.out.println("***********撤单后状态：");
            this.getTrandView(tradeId);

            //System.out.println("【挂单查询】:" + jsonpObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 撤单
     * 先查询挂单信息，然后在循环撤单
     */
    public void cancelTrade() {

        try {

            String method = "/api/v1/trade_list";
            Map map = new TreeMap();
            map.put("symbol", symbol);
            map.put("since", 0);
            map.put("type", "0");

            map.put("key", key);
            map.put("secret", secret);
            String sign = HttpUtils.getSignature(map);
            map.put("sign", sign);
            map.remove("secret");
            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
            JSONArray jsonArray = (JSONArray) jsonpObject.get("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String tradeId = jsonObject.get("id").toString();


                //撤单

                String method2 = "/api/v1/cancel_trade";

                Map<String, Object> canelMap = new TreeMap<>();
                canelMap.put("id", tradeId);

                canelMap.put("key", key);
                canelMap.put("secret", secret);
                String signCancel = HttpUtils.getSignature(canelMap);
                canelMap.put("sign", signCancel);
                canelMap.remove("secret");

                JSONObject jsonpObjectCancel = HttpUtils.sendPostRequestForJson(url + method2, canelMap);

                System.out.println("撤单开始：" + "，挂单ID:" + tradeId + ",撤单结果：" + jsonpObjectCancel.toString());

            }

            //System.out.println("【挂单查询】:" + jsonpObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public JSONObject getTrandView(String tradeId) {
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
            System.out.println("【查询订单信息】,tradeId=" + tradeId + "," + jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
