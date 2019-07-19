//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.owen.bithu.trade.api.huobi;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

public class ApiClient {
    private final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private AuthParams authParams;
    static final String API_HOST = "api.huobi.pro";
    static final String API_URL = "https://api.huobi.pro";

    public ApiClient() {
    }

    public static void main(String[] args) throws IOException {
        AuthParams authParams = new AuthParams();
        authParams.setAccessKeyId("d05d11dd-18397553-fe5e5190-de50a");
        authParams.setAccessKeySecret("5f7935d0-4c6bd4ae-e24c9ea3-79df1");

        ApiClient apiClient = new ApiClient();
        apiClient.setAuthParams(authParams);

        //
        apiClient.queryOrder(Long.valueOf(6078661818L));

//        List<Account> accounts = apiClient.getAccounts();
//        HuobiOrder huobiOrder = apiClient.queryOrder(Long.valueOf(6078661818L));
//        AccountBalance accountBalance = apiClient.accountBlance(((Account)accounts.get(0)).getId());
//        System.out.println(accountBalance);
//        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
//        createOrderRequest.setType("buy-limit");
//        createOrderRequest.setSymbol("");
//        apiClient.createOrder(createOrderRequest);
    }


    public void queryOrder(Long orderId) {
        JSONObject data = this.jsonGet("/v1/order/orders/" + orderId, (Map) null);
        System.out.println("queryOrder:" + data);
        //return data != null && "ok".equals(data.getString("status"))?(HuobiOrder)data.getObject("data", HuobiOrder.class):null;
    }

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
        return this.jsonCall(ApiClient.HttpType.GET, uri, (Object) null, (Map) (params == null ? new HashMap() : params));
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
    private JSONObject jsonCall(ApiClient.HttpType method, String uri, Object object, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap();
        }

        ApiSignature sign = new ApiSignature();
        sign.createSignature(this.authParams.getAccessKeyId(), this.authParams.getAccessKeySecret(), method == ApiClient.HttpType.GET ? "GET" : "POST", "api.huobi.pro", uri, (Map) params);
        String url = "https://api.huobi.pro" + uri;
        return method == ApiClient.HttpType.POST ? HttpUtils.sendJSONPost(url, this.toQueryString((Map) params), JSONObject.toJSONString(object), (String) null) : HttpUtils.sendJSONGet(url, this.toQueryString((Map) params), (String) null);
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
