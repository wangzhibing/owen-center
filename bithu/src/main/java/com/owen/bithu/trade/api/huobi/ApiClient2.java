//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.owen.bithu.trade.api.huobi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.bhex.api.client.domain.market.OrderBook;
import io.bhex.api.client.domain.market.OrderBookEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
//import site.binghai.coin.common.defination.CallBack;
//import site.binghai.coin.common.entity.AccountBalance;
//import site.binghai.coin.common.entity.HuobiOrder;
//import site.binghai.coin.common.entity.HuobiOrderBook;
//import site.binghai.coin.common.request.CreateOrderRequest;
//import site.binghai.coin.common.response.Account;
//import site.binghai.coin.common.response.Symbol;
//import site.binghai.coin.common.utils.CommonUtils;
//import site.binghai.coin.common.utils.HttpUtils;
//import site.binghai.coin.common.utils.JsonUtil;
public class ApiClient2 {
    private final Logger logger = LoggerFactory.getLogger(ApiClient2.class);

    private AuthParams authParams;
//        static final String API_HOST = "api.huobi.pro";
//    static final String API_URL = "https://api.huobi.pro";
//
    static final String API_HOST = "www.hpteco.com";
    static final String API_URL = "https://www.hpteco.com/api";

    String symbol = "addeos";

    public ApiClient2() {
    }

    public static void main(String[] args) throws IOException {

        /**张也**  account-id=654171*/
        //ExchangeAccountBo exchangeAccountBo = new ExchangeAccountBo("ca9cae56-8747dd6a-9112b932-a6fa3", "476bc4e7-1f9379d6-6503857c-63c5f");

        /**丽乔 account-id=8062579 **/
        ExchangeAccountBo exchangeAccountBo = new ExchangeAccountBo("e475fa02-ad0a115d-vf25treb80-05b83", "16ddf5f4-f2285f9e-aa1a2467-c0032");


        ApiClient2 apiClient = new ApiClient2();

        // apiClient.getOrderBook0(exchangeAccountBo);

        //
        //1.获取最新成交价
//        apiClient.getMarketTrade1(exchangeAccountBo);
//        apiClient.getMarketTrade2(exchangeAccountBo);
//
//        //获取买一价、卖一价
//        apiClient.getOrderBook1(exchangeAccountBo);
//        apiClient.getOrderBook2(exchangeAccountBo);
//
//        //获取账户:现货账户ID 654171
       apiClient.getAccounts1(exchangeAccountBo);
        //apiClient.getAccounts2(exchangeAccountBo);
//
//        //获取账户额度
//        apiClient.getAccountBalance1(exchangeAccountBo);
//        apiClient.getAccountBalance2(exchangeAccountBo);
//
//        //创建买单

        //apiClient.createOrder2(exchangeAccountBo);
//        apiClient.createOrder1(exchangeAccountBo);

        //撤单 39935119110 39935288392 39935363454 39935442400 39935807445 39939993209
//        apiClient.cancelOrder1(exchangeAccountBo, "40241648836");
//
//        apiClient.getCurrencys1(exchangeAccountBo);
//        apiClient.getCurrencys2(exchangeAccountBo);
        //6078661818L
        //40241648836
//        apiClient.queryOrder2(exchangeAccountBo, Long.valueOf(40241648836L));

//        List<Account> accounts = apiClient.getAccounts();
//        HuobiOrder huobiOrder = apiClient.queryOrder(Long.valueOf(6078661818L));
//        AccountBalance accountBalance = apiClient.accountBlance(((Account)accounts.get(0)).getId());
//        System.out.println(accountBalance);
//        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
//        createOrderRequest.setType("buy-limit");
//        createOrderRequest.setSymbol("");
//        apiClient.createOrder(createOrderRequest);
    }

    /******************************************************************************************************************************************************/
    public void getMarketTrade1(ExchangeAccountBo exchangeAccountBo) {
        String uri = "/market/trade";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap();
        params.put("symbol", "btcusdt");
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        JSONObject jsonObject = com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendGetRequestForJson(url, params);
        //{"ch":"market.btcusdt.trade.detail","tick":{"data":[{"amount":0.013210000000000000,"price":11408.690000000000000000,"id":10167980081339925708610,"ts":1562573625399,"direction":"buy"}],"id":101679800813,"ts":1562573625399},"status":"ok","ts":1562573625621}
        JSONObject tickJsonObject = (JSONObject) jsonObject.get("tick");
        JSONArray dataJsonArray = (JSONArray) tickJsonObject.get("data");
        JSONObject dataJsonObject = (JSONObject) dataJsonArray.get(0);
        System.out.println("getMarketTrade1.CurrentPrice" + dataJsonObject.get("price"));
    }

    public void getMarketTrade2(ExchangeAccountBo exchangeAccountBo) {
        String uri = "/market/trade";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap();
        params.put("symbol", "btcusdt");
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        System.out.println("getMarketTrade2:" + HttpUtils.sendJSONGet(url, this.toQueryString((Map) params), (String) null));
    }

    /******************************************************************************************************************************************************/
    public void getOrderBook0(ExchangeAccountBo exchangeAccountBo) {
        String uri = "/market/depth";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap();
        params.put("symbol", "btcusdt");
        params.put("type", "step0");
        params.put("depth", "35");
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        JSONObject jsonDeepthObject = com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendGetRequestForJson(url, params);
        JSONObject tickJsonObject = (JSONObject) jsonDeepthObject.get("tick");
        //买单
        JSONArray dataAllBidsJsonArray = (JSONArray) tickJsonObject.get("bids");
        //卖单
        JSONArray dataAllAsksJsonArray = (JSONArray) tickJsonObject.get("asks");
        OrderBook orderBook = new OrderBook();

        if (dataAllAsksJsonArray != null && dataAllAsksJsonArray.size() > 0) {
            List<OrderBookEntry> asks = new ArrayList<>(dataAllAsksJsonArray.size());
            OrderBookEntry orderBookAskEntry = null;
            for (Object jsonObject : dataAllAsksJsonArray) {
                JSONArray tempJSONOArray = (JSONArray) jsonObject;
                orderBookAskEntry = new OrderBookEntry();
                orderBookAskEntry.setPrice(tempJSONOArray.get(0).toString());
                orderBookAskEntry.setQty(tempJSONOArray.get(1).toString());
                asks.add(orderBookAskEntry);
            }
            orderBook.setAsks(asks);
        }

        if (dataAllBidsJsonArray != null && dataAllBidsJsonArray.size() > 0) {
            List<OrderBookEntry> bids = new ArrayList<>(dataAllBidsJsonArray.size());
            OrderBookEntry orderBookBidEntry = null;
            for (Object jsonObject : dataAllBidsJsonArray) {
                JSONArray tempJSONOArray = (JSONArray) jsonObject;
                orderBookBidEntry = new OrderBookEntry();
                orderBookBidEntry.setPrice(tempJSONOArray.get(0).toString());
                orderBookBidEntry.setQty(tempJSONOArray.get(1).toString());
                bids.add(orderBookBidEntry);
            }
            orderBook.setBids(bids);
        }


        //JSONArray dataSingleBidsJsonArray = (JSONArray) dataAllBidsJsonArray.get(0);
        //JSONArray dataSingleAsksJsonArray = (JSONArray) dataAllAsksJsonArray.get(0);
        System.out.println("getOrderBook0.orderBook:" + orderBook.toString());
    }

    public void getOrderBook1(ExchangeAccountBo exchangeAccountBo) {
        String uri = "/market/depth";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap();
        params.put("symbol", "btcusdt");
        params.put("type", "step0");
        params.put("depth", "5");
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        JSONObject jsonObject = com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendGetRequestForJson(url, params);
        JSONObject tickJsonObject = (JSONObject) jsonObject.get("tick");
        //买单
        JSONArray dataAllBidsJsonArray = (JSONArray) tickJsonObject.get("bids");
        //卖单
        JSONArray dataAllAsksJsonArray = (JSONArray) tickJsonObject.get("asks");
        JSONArray dataSingleBidsJsonArray = (JSONArray) dataAllBidsJsonArray.get(0);
        JSONArray dataSingleAsksJsonArray = (JSONArray) dataAllAsksJsonArray.get(0);
        System.out.println("getOrderBook1.buyPrice=" + dataSingleBidsJsonArray.get(0) + ",sellPrice=" + dataSingleAsksJsonArray.get(0));
    }

    public void getOrderBook2(ExchangeAccountBo exchangeAccountBo) {
        String uri = "/market/depth";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap();
        params.put("symbol", "btcusdt");
        params.put("type", "step0");
        params.put("depth", "10");
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        System.out.println("getOrderBook2:" + HttpUtils.sendJSONGet(url, this.toQueryString((Map) params), (String) null));
    }

    /******************************************************************************************************************************************************/
    public void getAccounts1(ExchangeAccountBo exchangeAccountBo) throws IOException {
        String uri = "/v1/account/accounts";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap<>();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        JSONObject jsonObject = com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendGetRequestForJson(url, params);
        JSONArray dataJsonArray = (JSONArray) jsonObject.get("data");
        JSONObject dataJsonObject = (JSONObject) dataJsonArray.get(0);
        System.out.println("getAccounts1.account-id:" + dataJsonObject.get("id"));
    }

    public void getAccounts2(ExchangeAccountBo exchangeAccountBo) throws IOException {
        String uri = "/v1/account/accounts";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap<>();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        System.out.println("getAccounts2:" + HttpUtils.sendJSONGet(url, this.toQueryString((Map) params), (String) null));
    }


    /******************************************************************************************************************************************************/
    public void getAccountBalance1(ExchangeAccountBo exchangeAccountBo) throws IOException {
        String uri = "/v1/account/accounts/8062579/balance";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap<>();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        JSONObject jsonObject = com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendGetRequestForJson(url, params);
        JSONObject dataJsonObject = (JSONObject) jsonObject.get("data");
        JSONArray listJsonObject = (JSONArray) dataJsonObject.get("list");
        for (int i = 0; i < listJsonObject.size(); i++) {
            JSONObject jsonObjectCC = (JSONObject) listJsonObject.get(i);
            if (jsonObjectCC.get("currency").toString().toUpperCase().equals("EOS") && jsonObjectCC.get("type").toString().equals("trade")) {
                System.out.println("getAccountBalance1:" + jsonObjectCC.get("balance"));
                break;
            }
        }
    }

    public void getAccountBalance2(ExchangeAccountBo exchangeAccountBo) throws IOException {
        String uri = "/v1/account/accounts/8062579/balance";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap<>();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        System.out.println("getAccountBalance1:" + HttpUtils.sendJSONGet(url, this.toQueryString((Map) params), (String) null));
    }

    /********************************************************************买单/卖单**********************************************************************************/
//    public void createOrder1(ExchangeAccountBo exchangeAccountBo) {
//        String uri = "/v1/order/orders/place";
//        Map<String, Object> params = new HashMap<>();
//        ApiSignature sign = new ApiSignature();
//        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "POST", API_HOST, uri, (Map) params);
//        String url = API_URL + uri;
//
//        Map createOrderMap = new TreeMap();
//        createOrderMap.put("symbol", "btcusdt");
//        createOrderMap.put("account-id", "654171");
//        createOrderMap.put("amount", 0.0001);
//        createOrderMap.put("price", 10000);
//        createOrderMap.put("type", "buy-limit");
//        createOrderMap.put("source", "api");
//        com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendPostRequestApplicationJsonForJson(url, params, createOrderMap);
//        System.out.println("createOrder1:" + com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendPostRequestApplicationJsonForJson(url, params, createOrderMap));
//    }

    public void createOrder2(ExchangeAccountBo exchangeAccountBo) {
        String uri = "/v1/order/orders/place";
        Map<String, Object> params = new HashMap<>();
        ApiSignature sign = new ApiSignature();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "POST", API_HOST, uri, (Map) params);
        String url = API_URL + uri;

        Map createOrderMap = new TreeMap();
        createOrderMap.put("symbol", "btcusdt");
        createOrderMap.put("account-id", "654171");
        createOrderMap.put("amount", 0.0001);
        createOrderMap.put("price", 10000);
        createOrderMap.put("type", "buy-limit");
        createOrderMap.put("source", "api");

        System.out.println("createOrder2:" + HttpUtils.sendJSONPost(url, this.toQueryString((Map) params), JSONObject.toJSONString(createOrderMap), (String) null));
    }

    /******************************************************************************************************************************************************/

    /********************************************************************买单/卖单**********************************************************************************/
    public void cancelOrder1(ExchangeAccountBo exchangeAccountBo, String orderId) {
        String uri = "/v1/order/orders/" + orderId + "/submitcancel";
        Map<String, Object> params = new HashMap<>();
        ApiSignature sign = new ApiSignature();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "POST", API_HOST, uri, (Map) params);
        String url = API_URL + uri;
        Map createOrderMap = new TreeMap();
      //  System.out.println("cancelOrder1:" + com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendPostRequestApplicationJsonForJson(url, params, createOrderMap));
    }


    public void cancelOrder2(ExchangeAccountBo exchangeAccountBo, String orderId) {
        String uri = "/v1/order/orders/" + orderId + "/submitcancel";
        Map<String, Object> params = new HashMap<>();
        ApiSignature sign = new ApiSignature();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "POST", API_HOST, uri, (Map) params);
        String url = API_URL + uri;
        Map createOrderMap = new TreeMap();
        System.out.println("cancelOrder1:" + HttpUtils.sendJSONPost(url, this.toQueryString((Map) params), JSONObject.toJSONString(createOrderMap), (String) null));
    }

    /******************************************************************************************************************************************************/

    public void getCurrencys1(ExchangeAccountBo exchangeAccountBo) throws IOException {
        String uri = "/v1/common/currencys";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap<>();
        //params.put("symbol", "btcusdt");
        String url = API_URL + uri;
        System.out.println("getCurrencys1:" + com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendGetRequest(url, params));
    }

    public void getCurrencys2(ExchangeAccountBo exchangeAccountBo) throws IOException {
        String uri = "/v1/common/currencys";
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap<>();
        //params.put("symbol", "btcusdt");
        //sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", "api.huobi.pro", uri, params);
        String url = API_URL + uri;
        System.out.println("getCurrencys2:" + HttpUtils.sendJSONGet(url, this.toQueryString((Map) params), (String) null));
    }

    /******************************************************************************************************************************************************/

    public void queryOrder2(ExchangeAccountBo exchangeAccountBo, Long orderId) {
        String uri = "/v1/order/orders/" + orderId;
        ApiSignature sign = new ApiSignature();
        Map<String, Object> params = new HashMap<>();
        sign.createSignature(exchangeAccountBo.getAccessKey(), exchangeAccountBo.getSecretKey(), "GET", API_HOST, uri, params);
        String url = API_URL + uri;
        System.out.println("queryOrder2:" + com.owen.bithu.trade.api.zgtop.util.HttpUtils.sendGetRequest(url, params));
    }

    public void queryOrder(ExchangeAccountBo exchangeAccountBo, Long orderId) {
        //JSONObject data = this.jsonGet("/v1/order/orders/" + orderId, (Map) null);
        String uri = "/v1/order/orders/" + orderId;
        ApiSignature sign = new ApiSignature();
        sign.createSignature(this.authParams.getAccessKeyId(), this.authParams.getAccessKeySecret(), "GET", API_HOST, uri, (Map) null);
        String url = API_URL + uri;
        HttpUtils.sendJSONGet(url, null, (String) null);
    }


    public void queryOrder(Long orderId) {
        JSONObject data = this.jsonGet("/v1/order/orders/" + orderId, (Map) null);
        System.out.println("queryOrder:" + data);
        //return data != null && "ok".equals(data.getString("status"))?(HuobiOrder)data.getObject("data", HuobiOrder.class):null;
    }

    /******************************************************************************************************************************************************/
    //    public Long allOnDealOf(Symbol symbol, double price, double btcBalance, long accountId) throws IOException {
//        if(btcBalance > 0.0D) {
//            CreateOrderRequest orderRequest = new CreateOrderRequest();
//            orderRequest.setAccountId(String.valueOf(accountId));
//            orderRequest.setSymbol(symbol.getBaseCurrency() + symbol.getQuoteCurrency());
//            orderRequest.setType("buy-limit");
//            orderRequest.setPrice(String.valueOf(price));
//            orderRequest.setAmount(CommonUtils.doubleSubCut(btcBalance / price, 2));
//            Long orderId = this.createOrder(orderRequest);
//            if(orderId != null && orderId.longValue() > 0L) {
//                this.logger.warn("创建订单成功! CreateOrderRequest:{} ,\n订单Id:{}", orderRequest, orderId);
//                return orderId;
//            }
//
//            this.logger.error("创建订单失败 ! CreateOrderRequest:{}", orderRequest);
//        }
//
//        return null;
//    }
//
//    public void waitOrderFilled(Long orderId, CallBack<HuobiOrder> callBack) throws IOException {
//        int maxErrorRetry = 200;
//        HuobiOrder huobiOrder = null;
//
//        do {
//            huobiOrder = this.queryOrder(orderId);
//            if(huobiOrder != null) {
//                this.logger.info("订单状态:{}", huobiOrder.getState());
//            } else {
//                this.logger.error("查询出错! orderId: {}", orderId);
//                if(maxErrorRetry-- < 0) {
//                    break;
//                }
//            }
//        } while(huobiOrder == null || !huobiOrder.getState().equals("filled"));
//
//        if(huobiOrder != null && huobiOrder.getType().equals("sell-limit")) {
//            this.logger.info("交易完成!");
//        } else if(huobiOrder != null) {
//            callBack.onCallBack(huobiOrder);
//        }
//
//        this.logger.error("坐等成交出错...");
//    }
//
//    public Long sellOrder(HuobiOrder order, double rate) throws IOException {
//        if(rate < 1.0D) {
//            rate = 1.1D;
//        }
//
//        double salePrice = Double.parseDouble(order.getPrice()) * rate;
//        if(salePrice > 0.0D && salePrice > Double.parseDouble(order.getPrice())) {
//            CreateOrderRequest orderRequest = new CreateOrderRequest();
//            orderRequest.setAccountId(String.valueOf(order.getAccountId()));
//            orderRequest.setSymbol(order.getSymbol());
//            orderRequest.setType("sell-limit");
//            orderRequest.setPrice(String.valueOf(salePrice));
//            orderRequest.setAmount(CommonUtils.doubleSubCut(Double.parseDouble(order.getAmount()), 6));
//            Long orderId = this.createOrder(orderRequest);
//            if(orderId != null && orderId.longValue() > 0L) {
//                this.logger.warn("卖出订单创建成功! CreateOrderRequest:{} ,\n订单Id:{}", orderRequest, orderId);
//                return orderId;
//            } else {
//                this.logger.error("卖出订单创建失败 ! CreateOrderRequest:{}", orderRequest);
//                return null;
//            }
//        } else {
//            this.logger.error("卖出价格不得低于买入价格!order: {}", order);
//            return null;
//        }
//    }
//
//    public double getBtcBalance() throws IOException {
//        double[] rs = new double[]{0.0D};
//        List<Account> accounts = this.getAccounts();
//        accounts.forEach((account) -> {
//            if(account.getType().equals("spot")) {
//                AccountBalance balance = this.accountBlance(account.getId());
//                balance.getList().forEach((cc) -> {
//                    if(cc.getCurrency().toUpperCase().equals("BTC")) {
//                        rs[0] += cc.getBalance();
//                    }
//
//                });
//            }
//
//        });
//        return rs[0];
//    }
//
//    public double getUsdtBalance() throws IOException {
//        double[] rs = new double[]{0.0D};
//        List<Account> accounts = this.getAccounts();
//        accounts.forEach((account) -> {
//            if(account.getType().equals("spot")) {
//                AccountBalance balance = this.accountBlance(account.getId());
//                balance.getList().forEach((cc) -> {
//                    if(cc.getCurrency().toUpperCase().equals("USDT")) {
//                        rs[0] += cc.getBalance();
//                    }
//
//                });
//            }
//
//        });
//        return rs[0];
//    }
//
//    public long getBtcAccountId() throws IOException {
//        long[] rs = new long[]{0L};
//        List<Account> accounts = this.getAccounts();
//        accounts.forEach((account) -> {
//            if(account.getType().equals("spot")) {
//                AccountBalance balance = this.accountBlance(account.getId());
//                balance.getList().forEach((cc) -> {
//                    if(cc.getCurrency().toUpperCase().equals("BTC") && cc.getBalance() > 0.0D) {
//                        rs[0] = account.getId();
//                    }
//
//                });
//            }
//
//        });
//        return rs[0];
//    }
//
//
//    public HuobiOrder queryOrder(Long orderId) {
//        JSONObject data = this.jsonGet("/v1/order/orders/" + orderId, (Map)null);
//        return data != null && "ok".equals(data.getString("status"))?(HuobiOrder)data.getObject("data", HuobiOrder.class):null;
//    }
//
//    public HuobiOrderBook getOrderBook(String symbol, String type) {
//        Map<String, Object> params = new HashMap();
//        params.put("symbol", symbol);
//        params.put("type", type == null?"step0":type);
//        JSONObject data = this.jsonGet("/market/depth", params);
//        return data != null && "ok".equals(data.getString("status"))?(HuobiOrderBook)JSON.parseObject(data.getJSONObject("tick").toJSONString(), HuobiOrderBook.class):null;
//    }
//
//    public List<Account> getAccounts() throws IOException {
//        JSONObject object = this.jsonGet("/v1/account/accounts", (Map)null);
//        JSONArray arr = object.getJSONArray("data");
//        return arr.toJavaList(Account.class);
//    }
//
//    public Long createOrder(CreateOrderRequest request) throws IOException {
//        JSONObject json = this.jsonCall(ApiClient.HttpType.POST, "/v1/order/orders/place", request, (Map)null);
//        if(json != null && "ok".equals(json.getString("status"))) {
//            return Long.valueOf(Long.parseLong(json.getString("data")));
//        } else {
//            throw new RuntimeException("创建订单失败,request: " + request + " , response" + json);
//        }
//    }
//
//    public boolean cancelOrder(String orderId) throws IOException {
//        JSONObject json = this.jsonCall(ApiClient.HttpType.POST, "/v1/order/orders/" + orderId + "/submitcancel", (Object)null, (Map)null);
//        return json != null && "ok".equals(json.getString("status"));
//    }
//
//    public String placeOrder(long orderId) throws IOException {
//        String resp = (String)this.post("/v1/order/orders/" + orderId + "/place", (Object)null, (Map)null, String.class);
//        return JSON.parseObject(resp).getString("data");
//    }
//
//    public AccountBalance accountBlance(long accountId) {
//        JSONObject data = this.jsonGet("/v1/account/accounts/" + accountId + "/balance", (Map)null);
//        return (AccountBalance)data.getObject("data", AccountBalance.class);
//    }
//
    private JSONObject jsonGet(String uri, Map<String, Object> params) {
        return this.jsonCall(ApiClient2.HttpType.GET, uri, (Object) null, (Map) (params == null ? new HashMap() : params));
    }

    //
//    <T> T post(String uri, Object data, Map<String, Object> params, Class<T> clazz) throws IOException {
//        return this.call(ApiClient.HttpType.POST, uri, data, params, clazz);
//    }
//
//    <T> T get(String uri, Map<String, Object> params, Class<T> clazz) throws IOException {
//        return this.call(ApiClient.HttpType.GET, uri, (Object)null, params, clazz);
//    }
//
    private JSONObject jsonCall(ApiClient2.HttpType method, String uri, Object object, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap();
        }

        ApiSignature sign = new ApiSignature();
        sign.createSignature(this.authParams.getAccessKeyId(), this.authParams.getAccessKeySecret(), method == ApiClient2.HttpType.GET ? "GET" : "POST", "api.huobi.pro", uri, (Map) params);
        String url = "https://api.huobi.pro" + uri;
        return method == ApiClient2.HttpType.POST ? HttpUtils.sendJSONPost(url, this.toQueryString((Map) params), JSONObject.toJSONString(object), (String) null) : HttpUtils.sendJSONGet(url, this.toQueryString((Map) params), (String) null);
    }

    //
//    private <T> T call(ApiClient.HttpType method, String uri, Object object, Map<String, Object> params, Class<T> clazz) throws IOException {
//        JSONObject jsonObject = this.jsonCall(method, uri, object, params);
//        return JSONObject.parseObject(jsonObject.toJSONString(), clazz);
//    }
//
//    String authData() {
//        MessageDigest md = null;
//
//        try {
//            md = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException var5) {
//            throw new RuntimeException(var5);
//        }
//
//        md.update(this.authParams.getAssetPassword().getBytes(StandardCharsets.UTF_8));
//        md.update("hello, moto".getBytes(StandardCharsets.UTF_8));
//        Map<String, String> map = new HashMap();
//        map.put("assetPwd", DatatypeConverter.printHexBinary(md.digest()).toLowerCase());
//
//        try {
//            return ApiSignature.urlEncode(JsonUtil.writeValue(map));
//        } catch (IOException var4) {
//            throw new RuntimeException("Get json failed: " + var4.getMessage());
//        }
//    }
//
    String toQueryString(Map<String, Object> params) {
        return String.join("&", (Iterable) params.entrySet().stream().map((entry) -> {
            return (String) entry.getKey() + "=" + ApiSignature.urlEncode(entry.getValue().toString());
        }).collect(Collectors.toList()));
    }

    public void setAuthParams(AuthParams authParams) {
        this.authParams = authParams;
    }

    private static enum HttpType {
        GET,
        POST;

        private HttpType() {
        }
    }
}
