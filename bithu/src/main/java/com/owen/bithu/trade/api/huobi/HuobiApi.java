//package com.owen.bithu.trade.api.huobi;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.time.DateFormatUtils;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.concurrent.Executor;
//
//
//
///**
// * zg.top APIDemo 示例
// */
//public class HuobiApi {
//
//    public static final String url = "https://zgtop.io/API/";
//    //**************深度铺单key******************/
//    private static final String deepkey = "aa6b8132da65548e4a0017151e6d4945b";
//    private static final String deepsecret = "e6d37e7b7bd63856b996b3faf6f58c8e";
//
//    //**************自成交key******************/
//    private static final String autoTradekey = "aa6b8132da65548e4a0017151e6d4945b";
//    private static final String autoTradesecret = "e6d37e7b7bd63856b996b3faf6f58c8e";
//
//    //设置初始值50
//    public volatile int xThreshold = 50;
//    public volatile BigDecimal autoTradeAccountInitFinance = BigDecimal.ZERO;
//    public volatile BigDecimal deepAccountInitFinance = BigDecimal.ZERO;
//
//    //4.变量4：为单位价格
//    public static final BigDecimal unitPrice = new BigDecimal("0.000001");
//    public static final String symbol = "87";
//
//    //查询当前交易信息
//    public static final String method = "/api/v1/ticker";
//    //挂单路径
//    public static final String methodTrade = "api/v1/trade";
//
//    public volatile BigDecimal twentyBuyPrice = BigDecimal.ZERO;
//
//    public volatile BigDecimal twentySellPrice = BigDecimal.ZERO;
//
//    public volatile boolean isZgtopStop = false;
//
//    @Autowired
//    private Executor taskExecutor;
//
//    public void excute() {
//
//        log.info("【ZgtopApi2】设置isZgtopStop=false");
//        this.isZgtopStop = false;
//        try {
//            taskExecutor.execute(() -> doControlXValue());
//            taskExecutor.execute(() -> doDeep());
//            Thread.sleep(2000L);
//            taskExecutor.execute(() -> autoTrade4());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void doSetZgStop() {
//        log.info("【ZgtopApi2】设置isZgtopStop=true");
//        this.isZgtopStop = true;
//    }
//
//    public static void main(String[] args) throws Exception {
//
//    }
//
//
//    /**
//     * 方案4
//     * 自动成交
//     */
//    public void autoTrade4() {
//
//        //0.symbol
//        //String symbol = "87";
//
//        //1.变量1：设置初始值, 当前价格初始值
//        //BigDecimal currentPrice = BigDecimal.ZERO;
//
//        //2.变量2：成交频率时间间隔 区间 【1~10】
//        long splitTimeMin = 1;
//        long splitTimeMax = 5;
//
//        //3.变量3：成交数量，几手，最后都是偶数，后续需要运算。
//        int tradeAccount = 0;
//
//        //4.变量4：为单位价格
//        //BigDecimal unitPrice = new BigDecimal("0.000001");
//
//        //5.变量5：随机买卖数量【1000~3000】
//        int vRangeInitValue = 400;
//        int vRangeMaxValue = 200;
//
//        //6.变量6：随机数，判断阈值
//        int xRange = 100;
//
//        Random randomX = new Random();
//
//        while (true) {
//            try {
//                log.info("自动成交开始******************");
//                //设置每次获取随机数X,范围是【0~100】
//                int x = randomX.nextInt(xRange);
//
//                //获取当前价格
//                String method = "/api/v1/ticker";
//                Map map = new HashMap();
//                map.put("symbol", symbol);
//                JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
//                JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
//                BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
//                BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
//                BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());
//
//
//                //对平均值判断处理
//                int sellCount = 0;
//                int buyCount = 0;
//                BigDecimal tradePrice = BigDecimal.ZERO;
//                if (x > xThreshold) {
//                    //当X>50自动挂价格（SELL1-i） 数量 V手 卖单，挂价格为(SELL1-i) V/2的买单
//                    sellCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
//                    buyCount = sellCount / 2;
//                    tradePrice = sellPrice.subtract(unitPrice);
//                    log.info("X>50........");
//
//                } else {
//                    //当X<50  自动挂 价格（SELL1） 数量为V手买单，价格为（SELL1） 数量为v/2额卖单
//                    buyCount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
//                    sellCount = buyCount / 2;
//                    tradePrice = sellPrice;
//                    log.info("X<50........");
//                }
//
//                //3.挂单：
//                String methodTrade = "api/v1/trade";
//                //3.1.卖一挂单信息
//                Map<String, Object> mapSellParmas = new TreeMap<>();
//                mapSellParmas.put("symbol", symbol);
//                mapSellParmas.put("amount", sellCount);
//                mapSellParmas.put("price", tradePrice);
//                mapSellParmas.put("type", "sell");
//                mapSellParmas.put("key", autoTradekey);
//                mapSellParmas.put("secret", autoTradesecret);
//                String sellSign = HttpUtils.getSignature(mapSellParmas);
//                mapSellParmas.put("sign", sellSign);
//                mapSellParmas.remove("secret");
//
//                //3.2.买一挂单信息
//                Map<String, Object> mapBuyParmas = new TreeMap<>();
//                mapBuyParmas.put("symbol", symbol);
//                mapBuyParmas.put("amount", buyCount);
//                mapBuyParmas.put("price", tradePrice);
//                mapBuyParmas.put("type", "buy");
//                mapBuyParmas.put("key", autoTradekey);
//                mapBuyParmas.put("secret", autoTradesecret);
//                String buySign = HttpUtils.getSignature(mapBuyParmas);
//                mapBuyParmas.put("sign", buySign);
//                mapBuyParmas.remove("secret");
//
//                //3.3.开始挂单
//                JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
//                log.info("【卖单jsonpSellObject】:" + JSON.toJSONString(jsonpSellObject));
//                JSONObject jsonBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
//                log.info("【买单jsonBuyObject】:" + JSON.toJSONString(jsonBuyObject));
//                log.info("时间" + DateFormatUtils.format(new Date(), "yyyy-mm-dd HH:mm:ss") + ",【买单】：订单ID：" + jsonBuyObject.get("id") + ",委托价格:" + tradePrice + ",委托数量:" + buyCount + "/n 【卖单】：订单ID：" + jsonpSellObject.get("id") + ",委托价格:" + tradePrice + ",委托数量:" + sellCount);
//
//                //每次做完一笔，需沉睡splitTime毫秒
//                log.info("自动成交解散******************");
//                Random ran = new Random();
//                Thread.sleep((1 + ran.nextInt(2)) * 1000);
//            } catch (Exception e) {
//                //有异常继续处理
//                e.printStackTrace();
//            }
//
//            if (isZgtopStop) {
//                //外部开关
//                log.info("【ZgtopApi2.autoTrade4】isZgtopStop=true,跳出doDeep");
//                break;
//            }
//        }
//    }
//
//
//    /**
//     * 深度处理
//     */
//    public void doDeep() {
//
//        //1.
//        doOpBuyLimit();
//
//        //2.
//        doOpSellLimit();
//
//        while (true) {
//            try {
//                log.info("深度处理开始*******");
//                boolean isRun = false;
//                //3获取当前价格
//                Map map = new HashMap();
//                map.put("symbol", symbol);
//                JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
//                JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
//                BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
//                BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
//                BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());
//
//                //预测价格
//                BigDecimal expectBuy100Price = buyPrice.subtract(unitPrice.multiply(new BigDecimal(35)));
//                BigDecimal expectSell100Price = sellPrice.add(unitPrice.multiply(new BigDecimal(35)));
//
//                //获取时时实际的买100，卖100的价格。
//                String method = "/api/v1/depth";
//                Map mapDeepth = new HashMap();
//                mapDeepth.put("symbol", symbol);
//                mapDeepth.put("merge", "4");
//                JSONObject jsonDeepthObject = HttpUtils.sendGetRequestForJson(url + method, mapDeepth);
//                log.info("getDepth:" + JSON.toJSONString(jsonDeepthObject));
//                //asks - 委买单[价格, 委单量]，价格从高到低排序
//                //bids - 委卖单[价格, 委单量]，价格从低到高排序
//                JSONArray buys = JSONObject.parseArray(JSONObject.parseObject(jsonDeepthObject.getString("data")).getString("asks"));
//                JSONArray sells = JSONObject.parseArray(JSONObject.parseObject(jsonDeepthObject.getString("data")).getString("bids"));
//
//                log.info("预期委托买价：" + expectBuy100Price);
//                log.info("预期委托卖价：" + expectSell100Price);
//                log.info("buys.size()=" + buys.size() + ",sells.size()=" + sells.size());
//                if (buys == null || buys.size() < 35 || sells == null || sells.size() < 35) {
//                    log.info("buys买单长度不够，进入深度。。。");
//                    int diffBuy = 35 - buys.size();
//                    JSONArray lastSingleBuyJsonArray = (JSONArray) buys.get(buys.size() - 1);
//                    BigDecimal diffBuyPrice = new BigDecimal(lastSingleBuyJsonArray.get(0).toString());
//                    doOpBuyLimit(diffBuy, diffBuyPrice);
//
//                    log.info("sells卖单长度不够，进入深度。。。");
//                    int diffSells = 35 - sells.size();
//                    JSONArray lastSingleSellJsonArray = (JSONArray) sells.get(buys.size() - 1);
//                    BigDecimal diffSellsPrice = new BigDecimal(lastSingleSellJsonArray.get(0).toString());
//                    doOpSellLimit(diffSells, diffSellsPrice);
//
//                    isRun = true;
//                } else {
//                    JSONArray singleJsonArray = (JSONArray) buys.get(35);
//                    BigDecimal singleBuyPrice = new BigDecimal(singleJsonArray.get(0).toString());
//                    BigDecimal diffUnit = expectBuy100Price.subtract(singleBuyPrice);
//                    log.info("buy>=35,但是价格不相等，相差：" + diffUnit);
//                    BigDecimal divideNum = diffUnit.divide(unitPrice);
//                    log.info("buy>=35,但是价格不相等，divideNum：" + divideNum + ",twentyBuyPrice【上次深度第20档买价格】=" + twentyBuyPrice + "，currentPrice=" + currentPrice);
//                    //第20档的价格是否>最新价格（买单）
//                    if (twentyBuyPrice.compareTo(currentPrice) > 0) {
//                        doOpBuyLimit();
//                        doOpSellLimit();
//                        isRun = true;
//                    }
//                }
//
//                log.info("【深度处理】zgTop.isRun1={}", isRun);
//                if (!isRun) {
//                    if (buys == null || buys.size() < 35 || sells == null || sells.size() < 35) {
//                        int diffBuy = 35 - buys.size();
//                        JSONArray lastSingleBuyJsonArray = (JSONArray) buys.get(buys.size() - 1);
//                        BigDecimal diffBuyPrice = new BigDecimal(lastSingleBuyJsonArray.get(0).toString());
//                        doOpBuyLimit(diffBuy, diffBuyPrice);
//
//                        log.info("sells卖单长度不够，进入深度。。。");
//                        int diffSells = 35 - sells.size();
//                        JSONArray lastSingleSellJsonArray = (JSONArray) sells.get(buys.size() - 1);
//                        BigDecimal diffSellsPrice = new BigDecimal(lastSingleSellJsonArray.get(0).toString());
//                        doOpSellLimit(diffSells, diffSellsPrice);
//                    } else {
//                        JSONArray singleJsonArray = (JSONArray) sells.get(35);
//                        BigDecimal singleSellPrice = new BigDecimal(singleJsonArray.get(0).toString());
//                        BigDecimal diffUnit = singleSellPrice.subtract(expectSell100Price);
//                        log.info("sell>=35,但是价格不相等，相差：" + diffUnit + ",twentySellPrice【上次深度第20档卖价格】=" + twentySellPrice + "，currentPrice=" + currentPrice);
//
//                        BigDecimal divideNum = diffUnit.divide(unitPrice);
//                        log.info("sell>=35,但是价格不相等，divideNum：" + divideNum);
//                        if (twentySellPrice.compareTo(currentPrice) < 0) {
//                            doOpBuyLimit();
//                            doOpSellLimit();
//                        }
//                    }
//                }
//
//                log.info("深度处理结束*******");
//                Thread.sleep(10000l);
//            } catch (Exception e) {
//                log.info("有异常。。。。。");
//                e.printStackTrace();
//            }
//
//            if (isZgtopStop) {
//                //外部开关
//                log.info("【ZgtopApi2.doDeep】isZgtopStop=true,跳出doDeep");
//                break;
//            }
//
//        }
//
//    }
//
//
//    public void doOpBuyLimit(int diff, BigDecimal price) {
//        log.info("进入doOpBuyLimit，diff:" + diff + ",price:" + price);
//
//        BigDecimal buyTradePrice = BigDecimal.ZERO;
//        int vRangeInitValue = 400;
//        int vRangeMaxValue = 200;
//        Random randomX = new Random();
//        for (int i = 1; i <= diff; i++) {
//            buyTradePrice = price.subtract(unitPrice.multiply(new BigDecimal(i)));
//            Map<String, Object> mapBuyParmas = new TreeMap<>();
//            mapBuyParmas.put("symbol", symbol);
//            mapBuyParmas.put("amount", vRangeInitValue + randomX.nextInt(vRangeMaxValue));
//            mapBuyParmas.put("price", buyTradePrice);
//            mapBuyParmas.put("type", "buy");
//            mapBuyParmas.put("key", deepkey);
//            mapBuyParmas.put("secret", deepsecret);
//            String buySign = HttpUtils.getSignature(mapBuyParmas);
//            mapBuyParmas.put("sign", buySign);
//            mapBuyParmas.remove("secret");
//
//            //3.3.开始挂单
//            JSONObject jsonpBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
//            log.info("判断差" + i + ",深度连续挂单【买】第" + i + "单,返回信息:" + jsonpBuyObject);
//        }
//    }
//
//    /**
//     * 挂买单
//     */
//    public void doOpBuyLimit() {
//        log.info("买挂单开始*******");
//        Long sTime = System.currentTimeMillis();
//        //1.获取当前价格
//        Map map = new HashMap();
//        map.put("symbol", symbol);
//        JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
//        JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
//        BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
//        BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
//        BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());
//
//        //2.刷单
//        int buyCount = 35;
//
//        //3.变量5：随机买卖数量【1000~3000】
//        int vRangeInitValue = 400;
//        int vRangeMaxValue = 200;
//        Random randomX = new Random();
//
//        BigDecimal buyTradePrice = BigDecimal.ZERO;
//        for (int i = 1; i <= buyCount; i++) {
//            buyTradePrice = buyPrice.subtract(unitPrice.multiply(new BigDecimal(i)));
//            Map<String, Object> mapBuyParmas = new TreeMap<>();
//            int amount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
//            mapBuyParmas.put("symbol", symbol);
//            mapBuyParmas.put("amount", amount);
//            mapBuyParmas.put("price", buyTradePrice);
//            mapBuyParmas.put("type", "buy");
//            mapBuyParmas.put("key", deepkey);
//            mapBuyParmas.put("secret", deepsecret);
//            String buySign = HttpUtils.getSignature(mapBuyParmas);
//            mapBuyParmas.put("sign", buySign);
//            mapBuyParmas.remove("secret");
//
//            if (i == 20) {
//                this.twentyBuyPrice = buyTradePrice;
//            }
//
//            //3.3.开始挂单
//            JSONObject jsonpBuyObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapBuyParmas);
//            log.info("时间" + DateFormatUtils.format(new Date(), "yyyy-mm-dd HH:mm:ss") + ",深度连续挂单【买】第" + i + "单,订单ID：" + jsonpBuyObject.get("id") + "委托价格：" + buyTradePrice + "委托数量：" + amount);
//        }
//        log.info("深度连续挂单【买】100单,耗时:" + (System.currentTimeMillis() - sTime));
//        log.info("买挂单结束*******");
//    }
//
//
//    public void doOpSellLimit(int diff, BigDecimal price) {
//        log.info("进入doOpSellLimit，diff:" + diff + ",price:" + price);
//
//        BigDecimal sellTradePrice = BigDecimal.ZERO;
//        int vRangeInitValue = 400;
//        int vRangeMaxValue = 200;
//        Random randomX = new Random();
//        for (int i = 1; i <= diff; i++) {
//            sellTradePrice = price.add(unitPrice.multiply(new BigDecimal(i)));
//            Map<String, Object> mapSellParmas = new TreeMap<>();
//            mapSellParmas.put("symbol", symbol);
//            mapSellParmas.put("amount", vRangeInitValue + randomX.nextInt(vRangeMaxValue));
//            mapSellParmas.put("price", sellTradePrice);
//            mapSellParmas.put("type", "sell");
//            mapSellParmas.put("key", deepkey);
//            mapSellParmas.put("secret", deepsecret);
//            String buySign = HttpUtils.getSignature(mapSellParmas);
//            mapSellParmas.put("sign", buySign);
//            mapSellParmas.remove("secret");
//
//            //3.3.开始挂单
//            JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
//            log.info("判断差" + i + ",深度连续挂单【卖】第" + i + "单,返回信息:" + jsonpSellObject);
//        }
//    }
//
//
//    /**
//     * 挂卖单
//     */
//    public void doOpSellLimit() {
//        log.info("卖挂单开始*******");
//        Long sTime = System.currentTimeMillis();
//        //1.获取当前价格
//
//        Map map = new HashMap();
//        map.put("symbol", symbol);
//        JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
//        JSONObject dataJSONObject = (JSONObject) jsonpObject.get("data");
//        BigDecimal currentPrice = new BigDecimal(dataJSONObject.get("last").toString());
//        BigDecimal buyPrice = new BigDecimal(dataJSONObject.get("buy").toString());
//        BigDecimal sellPrice = new BigDecimal(dataJSONObject.get("sell").toString());
//
//        //2.刷单
//        int sellCount = 35;
//
//        //3.变量5：随机买卖数量【1000~3000】
//        int vRangeInitValue = 400;
//        int vRangeMaxValue = 200;
//        Random randomX = new Random();
//
//        BigDecimal sellTradePrice = BigDecimal.ZERO;
//        for (int i = 1; i <= sellCount; i++) {
//            sellTradePrice = sellPrice.add(unitPrice.multiply(new BigDecimal(i)));
//            int amount = vRangeInitValue + randomX.nextInt(vRangeMaxValue);
//            Map<String, Object> mapSellParmas = new TreeMap<>();
//            mapSellParmas.put("symbol", symbol);
//            mapSellParmas.put("amount", amount);
//            mapSellParmas.put("price", sellTradePrice);
//            mapSellParmas.put("type", "sell");
//            mapSellParmas.put("key", deepkey);
//            mapSellParmas.put("secret", deepsecret);
//            String buySign = HttpUtils.getSignature(mapSellParmas);
//            mapSellParmas.put("sign", buySign);
//
//            mapSellParmas.remove("secret");
//            if (i == 20) {
//                this.twentySellPrice = sellTradePrice;
//            }
//
//            //3.3.开始挂单
//            JSONObject jsonpSellObject = HttpUtils.sendPostRequestForJson(url + methodTrade, mapSellParmas);
//            log.info("时间" + DateFormatUtils.format(new Date(), "yyyy-mm-dd HH:mm:ss") + ",深度连续挂单【卖】第" + i + "单,订单ID：" + jsonpSellObject.get("id") + "委托价格：" + sellTradePrice + "委托数量：" + amount);
//        }
//        log.info("深度连续挂单【卖】100单,耗时:" + (System.currentTimeMillis() - sTime));
//        log.info("卖挂单结束*******");
//    }
//
//
//    /**
//     * 根据每隔5分钟，设置xThreshold变量值
//     */
//    private void doControlXValue() {
//        //1.第一次
//        autoTradeAccountInitFinance = doGetAutoTradeAccountFinance();
//        xThreshold = 50;
//        log.info("【控制】xThreshold，symbol={},autoTradeAccountInitFinance={}", symbol, autoTradeAccountInitFinance);
//        BigDecimal currentAutoTradeAccountFinance = BigDecimal.ZERO;
//        while (true) {
//            try {
//                //每隔25秒获取一次时时资产
//                Thread.sleep(25 * 1000L);
//                currentAutoTradeAccountFinance = doGetAutoTradeAccountFinance();
//                BigDecimal divideNum = currentAutoTradeAccountFinance.divide(autoTradeAccountInitFinance, 2, BigDecimal.ROUND_HALF_DOWN);
//                log.info("【控制】xThreshold，symbol={},autoTradeAccountInitFinance={},currentAutoTradeAccountFinance={},divideNum={}", symbol, autoTradeAccountInitFinance, currentAutoTradeAccountFinance, divideNum);
//                if (divideNum.compareTo(new BigDecimal("1.8")) > 0) {
//                    xThreshold = 80;
//                } else if (divideNum.compareTo(new BigDecimal("0.2")) < 0) {
//                    xThreshold = 20;
//                } else {
//                    xThreshold = 50;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private BigDecimal doGetAutoTradeAccountFinance() {
//        String method = "/api/v1/balances";
//        Map map = new TreeMap<>();
//        map.put("key", autoTradekey);
//        map.put("secret", autoTradesecret);
//        String sign = HttpUtils.getSignature(map);
//        map.put("sign", sign);
//        map.remove("secret");
//        JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
//        JSONArray jsonArrayData = (JSONArray) jsonpObject.get("data");
//        for (int i = 0; i < jsonArrayData.size(); i++) {
//            JSONObject tempJsonObject = (JSONObject) jsonArrayData.get(i);
//            if (tempJsonObject.get("name").toString().equals("USDT")) {
//                BigDecimal finance = new BigDecimal(tempJsonObject.get("balance").toString());
//                log.info("获取{}-USDT，assetBalance={}", symbol, finance);
//                return finance;
//            }
//        }
//        return BigDecimal.ZERO;
//    }
//
//    /**
//     * 撤单处理
//     */
//    private void cancelOrder(Set<String> cancelOrderIds) {
//        if (cancelOrderIds == null || cancelOrderIds.isEmpty()) {
//            return;
//        }
//
//        for (String orderId : cancelOrderIds) {
//            try {
//                String method2 = "/api/v1/cancel_trade";
//                Map<String, Object> canelMap = new TreeMap<>();
//                canelMap.put("id", orderId);
//                canelMap.put("key", deepkey);
//                canelMap.put("secret", deepsecret);
//                String signCancel = HttpUtils.getSignature(canelMap);
//                canelMap.put("sign", signCancel);
//                canelMap.remove("secret");
//                JSONObject jsonpObjectCancel = HttpUtils.sendPostRequestForJson(url + method2, canelMap);
//                System.out.println(jsonpObjectCancel.toString());
//                log.info("【撤单处理】【{}】orderId={},撤单结果={}", symbol, orderId, jsonpObjectCancel);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    /**
//     * 1. 获取当前最新行情
//     * 参数
//     * symbol - 1,2...( 区块链资产对照表)
//     * {"code": 200,"data": {"high": 0.0,"vol": 0.0,"last": 0.999,"low": 0.0,"buy": 0.0,"sell": 0.0}}
//     */
//    public Map<String, BigDecimal> getPriceBySymbol(String symbol) {
//        Map<String, BigDecimal> resMap = new HashMap<>();
//        try {
//            String method = "/api/v1/ticker";
//            Map map = new HashMap();
//            map.put("symbol", symbol);
//            JSONObject jsonObject = HttpUtils.sendGetRequestForJson(url + method, map);
//            String code = jsonObject.get("code").toString();
//            if (code.equals(ZgtopConstant.HTTP_SUCCESS_CODE)) {
//                JSONObject jsonObjectData = (JSONObject) jsonObject.get("data");
//                BigDecimal buyOne = new BigDecimal(jsonObjectData.get("buy").toString());
//                BigDecimal sellOne = new BigDecimal(jsonObjectData.get("sell").toString());
//                log.info("【买一价：" + buyOne + "，卖一价" + sellOne + "】");
//                resMap.put("buy", buyOne);
//                resMap.put("sell", sellOne);
//            } else {
//                log.info("【获取当前最新行情】失败，code=" + code + ",消息=" + errorMapping.get(code));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resMap;
//    }
//
////    /**
////     * 根据策略返回实际价格
////     *
////     * @param buyOne
////     * @param sellOne
////     * @return
////     */
////    private BigDecimal getActivePrice(BigDecimal buyOne, BigDecimal sellOne) {
////
////        //1. strategy 1:random
////        buyOne.add((sellOne.subtract(buyOne)).divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP));
////
////        BigDecimal diff = sellOne.subtract(buyOne);
////
////        //2.种子为10(可配置，随机取数按比例)
////        float randomIndex = new Random().nextInt(10);
////        if (randomIndex == 0) {
////            randomIndex = 1;
////        }
////
////        float rate = randomIndex / 10;
////        return buyOne.add(diff.multiply(new BigDecimal(rate)), new MathContext(4, RoundingMode.HALF_UP));
////    }
//
//
////    /**
////     * 撤单
////     * 先查询挂单信息，然后在循环撤单
////     */
////    public void cancelTrade() {
////
////        try {
////
////            String method = "/api/v1/trade_list";
////            Map map = new TreeMap();
////            map.put("symbol", symbol);
////            map.put("since", 0);
////            map.put("type", "0");
////
////            map.put("key", key);
////            map.put("secret", secret);
////            String sign = HttpUtils.getSignature(map);
////            map.put("sign", sign);
////            map.remove("secret");
////            JSONObject jsonpObject = HttpUtils.sendGetRequestForJson(url + method, map);
////            JSONArray jsonArray = (JSONArray) jsonpObject.get("data");
////            for (int i = 0; i < jsonArray.size(); i++) {
////                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
////                String tradeId = jsonObject.get("id").toString();
////
////
////                //撤单
////
////                String method2 = "/api/v1/cancel_trade";
////
////                Map<String, Object> canelMap = new TreeMap<>();
////                canelMap.put("id", tradeId);
////
////                canelMap.put("key", key);
////                canelMap.put("secret", secret);
////                String signCancel = HttpUtils.getSignature(canelMap);
////                canelMap.put("sign", signCancel);
////                canelMap.remove("secret");
////
////                JSONObject jsonpObjectCancel = HttpUtils.sendPostRequestForJson(url + method2, canelMap);
////
////                log.info("撤单开始：" + "，挂单ID:" + tradeId + ",撤单结果：" + jsonpObjectCancel.toString());
////
////            }
////
////            //log.info("【挂单查询】:" + jsonpObject.toString());
////        } catch (Exception e) {
////            log.info("有异常。。。。。");
////            e.printStackTrace();
////        }
////    }
//
////
////    public JSONObject getTrandView(String tradeId) {
////        try {
////
////            String method = "/api/v1/trade_view";
////
////            Map map = new TreeMap();
////            map.put("id", tradeId);
////
////            map.put("key", key);
////            map.put("secret", secret);
////            String sign = HttpUtils.getSignature(map);
////            map.put("sign", sign);
////            map.remove("secret");
////
////            JSONObject jsonObject = HttpUtils.sendGetRequestForJson(url + method, map);
////            log.info("【查询订单信息】,tradeId=" + tradeId + "," + jsonObject.toString());
////            return jsonObject;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
//
//
//}
