package com.owen.bithu.trade.api.biki;


import com.google.common.collect.Sets;
import io.bhex.api.client.BHexApiClientFactory;
import io.bhex.api.client.BHexApiRestClient;
import io.bhex.api.client.domain.account.*;
import io.bhex.api.client.domain.account.request.OpenOrderRequest;
import io.bhex.api.client.domain.market.OrderBook;
import io.bhex.api.client.domain.market.TickerPrice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * zg.top APIDemo 示例
 */
public class BiGooApi {

    private static final String key = "MVU0iinRElZjn3irH25s2fan1FuBS4XGklx7KFIlmfQoUFChcGO52jZZuChA2d9o";
    private static final String secret = "UFHwlkFpD8oXkTY2IgeNf13SFALmJ0AWyABG5rRlYePiUPHoCOOimkmxzrUA8y1N";
    private static final String baseUrl = "https://api.bigoo.pro/";
    private BHexApiRestClient bHexApiRestClient;
    {
        BHexApiClientFactory factory = BHexApiClientFactory.newInstance(key, secret);
        bHexApiRestClient = factory.newRestClient();
    }

    //4.变量4：为单位价格
    public static final BigDecimal unitPrice = new BigDecimal("0.0001");
    public static final String symbol = "GRINUSDT";
    private static final Integer autoTradeInitAmount = 2;
    private static final Integer autoTradeMaxAmount = 2;

    private static final Integer orderDepth = 3;

    public static void main(String[] args) throws Exception {
        BiGooApi zgtopApi = new BiGooApi();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            try{
                zgtopApi.autoTrade2();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        });
        executorService.submit(() -> {
            try{
                zgtopApi.doDeep();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        });
//        zgtopApi.autoTrade2();
//        zgtopApi.autoTrade3();
//        zgtopApi.doDeep();

    }



    /**
     * 方案2
     * 自动成交
     */
    public void autoTrade2() throws Exception {

        //6.变量6：随机数，判断阈值
        int xRange = 100;


        Random randomX = new Random();


        while (true) {
            //设置每次获取随机数X,范围是【0~100】
            int x = randomX.nextInt(xRange);

            //获取当前价格
            TickerPrice bookTicker = bHexApiRestClient.getPrice(symbol);
            BigDecimal currentPrice = new BigDecimal(bookTicker.getPrice());


            //对平均值判断处理
            int sellCount = 0;
            int buyCount = 0;
            BigDecimal tradePrice = BigDecimal.ZERO;
            if (x > 50) {
                //当X>50自动挂价格（C+i） 数量 V手 卖单， 挂价格为(C+i) V/2的买单
                sellCount = tradeAmount();
                buyCount = sellCount / 2;
                tradePrice = currentPrice.add(unitPrice);
            } else {
                //当X<50  自动挂 价格（c-i） 数量为V手买单，价格为（c-i） 数量为v/2额卖单
                buyCount = tradeAmount();
                sellCount = buyCount / 2;
                tradePrice = currentPrice.subtract(unitPrice);
            }

            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, sellCount + "", tradePrice.toPlainString()));
            System.out.println("自动成交卖单订单返回值 " + sellResponse);
            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, buyCount + "", tradePrice.toPlainString()));
            System.out.println("自动成交买单订单返回值 " + buyResponse);

            //每次做完一笔，需沉睡splitTime毫秒
            System.out.println("******************");
            Random ran = new Random();
            ran.nextInt(5);
            Thread.sleep((1 + ran.nextInt(5)) * 1000);
        }

    }


    /**
     * 方案3
     * 自动成交
     */
    public void autoTrade3() throws Exception {

        //6.变量6：随机数，判断阈值
        int xRange = 100;


        Random randomX = new Random();


        while (true) {
            //设置每次获取随机数X,范围是【0~100】
            int x = randomX.nextInt(xRange);

            //获取当前价格

            OrderBook orderBook = bHexApiRestClient.getOrderBook(symbol, 100);
            BigDecimal buyPrice = new BigDecimal(orderBook.getBids().get(0).getPrice());
            BigDecimal sellPrice = new BigDecimal(orderBook.getAsks().get(0).getPrice());


            //对平均值判断处理
            int sellCount = 0;
            int buyCount = 0;
            BigDecimal tradePrice = BigDecimal.ZERO;
            if (x > 50) {
                //当X>50自动挂价格（SELL1-i） 数量 V手 卖单，挂价格为(SELL1-i) V/2的买单
                sellCount = autoTradeInitAmount + randomX.nextInt(autoTradeMaxAmount);
                buyCount = sellCount / 2;
                tradePrice = sellPrice.subtract(unitPrice);
            } else {
                //当X<50  自动挂 价格（BUY1+i） 数量为V手买单，价格为（BUY1+i） 数量为v/2额卖单
                buyCount = autoTradeInitAmount + randomX.nextInt(autoTradeMaxAmount);
                sellCount = buyCount / 2;
                tradePrice = buyPrice.add(unitPrice);
            }

            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, sellCount + "", tradePrice.toPlainString()));
            System.out.println("自动成交卖单订单返回值 " + sellResponse);
            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, buyCount + "", tradePrice.toPlainString()));
            System.out.println("自动成交买单订单返回值 " + buyResponse);

            //每次做完一笔，需沉睡splitTime毫秒
            System.out.println("******************");
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

            List<Order> openOrders = bHexApiRestClient.getOpenOrders(new OpenOrderRequest());
            List<Order> buyOrders = openOrders.stream().filter(order -> order.getSide() == OrderSide.BUY).collect(Collectors.toList());
            List<Order> sellOrders = openOrders.stream().filter(order -> order.getSide() == OrderSide.SELL).collect(Collectors.toList());

            Set<String> buyPrices = Sets.newHashSet();
            Set<String> sellPrices = Sets.newHashSet();
            for(Order order : buyOrders){
                buyPrices.add(order.getPrice());
            }
            for(Order order : sellOrders){
                sellPrices.add(order.getPrice());
            }
            System.out.println("当前买单价位数量 " + buyPrices.size());
            System.out.println("当前卖单价位数量 " + sellPrices.size());

            TickerPrice bookTicker = bHexApiRestClient.getPrice(symbol);
            BigDecimal currentPrice = new BigDecimal(bookTicker.getPrice());
            if(buyPrices.size() < orderDepth){
                for (int i = 1; i <= orderDepth; i++) {
                    BigDecimal tradePrice = currentPrice.subtract(unitPrice.multiply(new BigDecimal(i)));
                    Integer tradeAmount = tradeAmount();
                    Boolean isNotExist = buyOrders.stream().noneMatch(order -> new BigDecimal(order.getPrice()).equals(tradePrice));
                    if(isNotExist){
                        NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, tradeAmount + "", tradePrice.toPlainString()));
                        System.out.println("补充买单" + buyResponse);
                    }
                }
            }

            if(sellPrices.size() < orderDepth){
                for (int i = 1; i <= orderDepth; i++) {
                    BigDecimal tradePrice = currentPrice.add(unitPrice.multiply(new BigDecimal(i)));
                    Integer tradeAmount = tradeAmount();
                    Boolean isNotExist = sellOrders.stream().noneMatch(order -> new BigDecimal(order.getPrice()).equals(tradePrice));
                    if(isNotExist){
                        NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, tradeAmount + "", tradePrice.toPlainString()));
                        System.out.println("补充卖单" + buyResponse);
                    }
                }
            }

            System.out.println("深度处理结束*******");

            Thread.sleep(10000l);
        }

    }

    /**
     * 挂买单
     */
    public void doOpBuyLimit() {
        System.out.println("买挂单开始*******");
        Long sTime = System.currentTimeMillis();
        //1.获取当前价格
        BigDecimal currentPrice = new BigDecimal(bHexApiRestClient.getPrice(symbol).getPrice());

        //2.刷单

        for (int i = 1; i <= orderDepth; i++) {
            BigDecimal buyTradePrice = currentPrice.subtract(unitPrice.multiply(new BigDecimal(i)));
            int amount = tradeAmount();
            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, amount + "", buyTradePrice.toPlainString()));
            System.out.println("深度连续挂单【买】第" + i + "单,返回信息:" + buyResponse);
        }
        System.out.println("深度连续挂单【买】100单,耗时:" + (System.currentTimeMillis() - sTime));
        System.out.println("买挂单结束*******");
    }

    /**
     * 挂卖单
     */
    public void doOpSellLimit() {
        System.out.println("卖挂单开始*******");
        Long sTime = System.currentTimeMillis();
        //1.获取当前价格

        BigDecimal currentPrice = new BigDecimal(bHexApiRestClient.getPrice(symbol).getPrice());



        for (int i = 1; i <= orderDepth; i++) {
            BigDecimal sellTradePrice = currentPrice.add(unitPrice.multiply(new BigDecimal(i)));

            int amount = tradeAmount();
            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, amount + "", sellTradePrice.toPlainString()));
            System.out.println("深度连续挂单【买】第" + i + "单,返回信息:" + sellResponse);
        }

        System.out.println("深度连续挂单【卖】100单,耗时:" + (System.currentTimeMillis() - sTime));
        System.out.println("卖挂单结束*******");
    }

    private static Integer tradeAmount(){
        return autoTradeInitAmount + new Random().nextInt(autoTradeMaxAmount);
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

}
