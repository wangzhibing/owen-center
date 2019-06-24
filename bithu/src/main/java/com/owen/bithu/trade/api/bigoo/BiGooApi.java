package com.owen.bithu.trade.api.bigoo;


import io.bhex.api.client.BHexApiClientFactory;
import io.bhex.api.client.BHexApiRestClient;
import io.bhex.api.client.domain.account.NewOrder;
import io.bhex.api.client.domain.account.NewOrderResponse;
import io.bhex.api.client.domain.account.TimeInForce;
import io.bhex.api.client.domain.market.OrderBook;
import io.bhex.api.client.domain.market.TickerPrice;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * zg.top APIDemo 示例
 */
public class BiGooApi {

    private static final String key = "MVU0iinRElZjn3irH25s2fan1FuBS4XGklx7KFIlmfQoUFChcGO52jZZuChA2d9o";
    private static final String secret = "UFHwlkFpD8oXkTY2IgeNf13SFALmJ0AWyABG5rRlYePiUPHoCOOimkmxzrUA8y1N";
    private static final String baseUrl = "https://api.bigoo.pro/";

    public volatile BigDecimal twentyBuyPrice = BigDecimal.ZERO;
    public volatile BigDecimal twentySellPrice = BigDecimal.ZERO;

    private BHexApiRestClient bHexApiRestClient;
    {
        BHexApiClientFactory factory = BHexApiClientFactory.newInstance(baseUrl,key, secret);
        bHexApiRestClient = factory.newRestClient();
    }

    //4.变量4：为单位价格
    public static final BigDecimal unitPrice = new BigDecimal("0.000001");
    public static final String symbol = "LAMBUSDT";

    //价格初始值
    private static final Integer autoTradeInitAmount = 2;
    private static final Integer autoTradeMaxAmount = 2;

    private static final Integer orderDepth = 35;

    public static void main(String[] args) throws Exception {

        BiGooApi zgtopApi = new BiGooApi();
        //gtopApi.autoTrade4();

        //zgtopApi.doDeep();

        //*************核心方法************·
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            try{
                zgtopApi.doDeep();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        });

        Thread.sleep(2000L);
        executorService.submit(() -> {
            try{
                zgtopApi.autoTrade4();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        });
        //*************核心方法*********
//        zgtopApi.autoTrade2();
//        zgtopApi.autoTrade3();
//        zgtopApi.doDeep();

    }



//    /**
//     * 方案2
//     * 自动成交
//     */
//    public void autoTrade2() throws Exception {
//
//        //6.变量6：随机数，判断阈值
//        int xRange = 100;
//        Random randomX = new Random();
//
//        while (true) {
//            //设置每次获取随机数X,范围是【0~100】
//            int x = randomX.nextInt(xRange);
//
//            //获取当前价格
//            TickerPrice bookTicker = bHexApiRestClient.getPrice(symbol);
//            BigDecimal currentPrice = new BigDecimal(bookTicker.getPrice());
//
//            //4.huo获取当前买一价、卖一价
//            OrderBook orderBook = bHexApiRestClient.getOrderBook(symbol, 1);
//            BigDecimal buyPrice = new BigDecimal(orderBook.getBids().get(0).getPrice());
//            BigDecimal sellPrice = new BigDecimal(orderBook.getAsks().get(0).getPrice());
//
//            //对平均值判断处理
//            int sellCount = 0;
//            int buyCount = 0;
//            BigDecimal tradePrice = BigDecimal.ZERO;
//            if (x > 50) {
//                //当X>50自动挂价格（C+i） 数量 V手 卖单， 挂价格为(C+i) V/2的买单
//                sellCount = tradeAmount();
//                buyCount = sellCount / 2;
//                tradePrice = currentPrice.add(unitPrice);
//            } else {
//                //当X<50  自动挂 价格（c-i） 数量为V手买单，价格为（c-i） 数量为v/2额卖单
//                buyCount = tradeAmount();
//                sellCount = buyCount / 2;
//                tradePrice = currentPrice.subtract(unitPrice);
//            }
//
//            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, sellCount + "", tradePrice.toPlainString()));
//            System.out.println("自动成交卖单订单返回值 " + sellResponse);
//            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, buyCount + "", tradePrice.toPlainString()));
//            System.out.println("自动成交买单订单返回值 " + buyResponse);
//
//            //每次做完一笔，需沉睡splitTime毫秒
//            System.out.println("******************");
//            Random ran = new Random();
//            ran.nextInt(5);
//            Thread.sleep((1 + ran.nextInt(5)) * 1000);
//        }
//
//    }
//
//
//    /**
//     * 方案3
//     * 自动成交
//     */
//    public void autoTrade3() throws Exception {
//
//        //6.变量6：随机数，判断阈值
//        int xRange = 100;
//        Random randomX = new Random();
//
//        while (true) {
//            //设置每次获取随机数X,范围是【0~100】
//            int x = randomX.nextInt(xRange);
//
//            //获取当前价格
//            OrderBook orderBook = bHexApiRestClient.getOrderBook(symbol, 100);
//            BigDecimal buyPrice = new BigDecimal(orderBook.getBids().get(0).getPrice());
//            BigDecimal sellPrice = new BigDecimal(orderBook.getAsks().get(0).getPrice());
//
//
//            //对平均值判断处理
//            int sellCount = 0;
//            int buyCount = 0;
//            BigDecimal tradePrice = BigDecimal.ZERO;
//            if (x > 50) {
//                //当X>50自动挂价格（SELL1-i） 数量 V手 卖单，挂价格为(SELL1-i) V/2的买单
//                sellCount = autoTradeInitAmount + randomX.nextInt(autoTradeMaxAmount);
//                buyCount = sellCount / 2;
//                tradePrice = sellPrice.subtract(unitPrice);
//            } else {
//                //当X<50  自动挂 价格（BUY1+i） 数量为V手买单，价格为（BUY1+i） 数量为v/2额卖单
//                buyCount = autoTradeInitAmount + randomX.nextInt(autoTradeMaxAmount);
//                sellCount = buyCount / 2;
//                tradePrice = buyPrice.add(unitPrice);
//            }
//
//            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, sellCount + "", tradePrice.toPlainString()));
//            System.out.println("自动成交卖单订单返回值 " + sellResponse);
//            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, buyCount + "", tradePrice.toPlainString()));
//            System.out.println("自动成交买单订单返回值 " + buyResponse);
//
//            //每次做完一笔，需沉睡splitTime毫秒
//            System.out.println("******************");
//            Random ran = new Random();
//            Thread.sleep((1 + ran.nextInt(10)) * 1000);
//        }
//    }


    /**
     * 方案4
     * 自动成交
     */
    public void autoTrade4() throws Exception {
        System.out.println("************自动成交开始***********");

        //6.变量6：随机数，判断阈值
        int xRange = 100;
        Random randomX = new Random();

        while (true) {
            //设置每次获取随机数X,范围是【0~100】
            int x = randomX.nextInt(xRange);

            //获取当前价格
            TickerPrice bookTicker = bHexApiRestClient.getPrice(symbol);
            BigDecimal currentPrice = new BigDecimal(bookTicker.getPrice());

            //4.huo获取当前买一价、卖一价
            OrderBook orderBook = bHexApiRestClient.getOrderBook(symbol, 1);
            BigDecimal buyPrice = new BigDecimal(orderBook.getBids().get(0).getPrice());
            BigDecimal sellPrice = new BigDecimal(orderBook.getAsks().get(0).getPrice());

            //对平均值判断处理
            BigDecimal sellCount = BigDecimal.ZERO;
            BigDecimal buyCount = BigDecimal.ZERO;
            BigDecimal tradePrice = BigDecimal.ZERO;
            if (x > 50) {
                //当X>50自动挂价格（SELL1-i） 数量 V手 卖单，挂价格为(SELL1-i) V/2的买单
                sellCount = tradeAmount();
                buyCount = sellCount.divide(new BigDecimal(2));
                tradePrice = sellPrice.subtract(unitPrice);
            } else {
                //当X<50  自动挂 价格（SELL1） 数量为V手买单，价格为（SELL1） 数量为v/2额卖单
                buyCount = tradeAmount();
                sellCount = buyCount.divide(new BigDecimal(2));
                tradePrice = sellPrice;
            }

            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, buyCount + "", tradePrice.toPlainString()));
            System.out.println("自动成交买单订单返回值：" + buyResponse);

            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, sellCount + "", tradePrice.toPlainString()));
            System.out.println("自动成交卖单订单返回值：" + sellResponse);

            //每次做完一笔，需沉睡splitTime毫秒
            System.out.println("************自动成交结束***********");
            Random ran = new Random();
            Thread.sleep((7 + ran.nextInt(4)) * 1000);
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
            BigDecimal currentPrice = new BigDecimal(bHexApiRestClient.getPrice(symbol).getPrice());

            //4.获取深度
            OrderBook orderBook = bHexApiRestClient.getOrderBook(symbol, orderDepth);
            BigDecimal buyPrice = new BigDecimal(orderBook.getBids().get(0).getPrice());
            BigDecimal sellPrice = new BigDecimal(orderBook.getAsks().get(0).getPrice());

            //5.预测价格
            BigDecimal expectBuy100Price = currentPrice.subtract(unitPrice.multiply(new BigDecimal(orderDepth)));
            BigDecimal expectSell100Price = currentPrice.add(unitPrice.multiply(new BigDecimal(orderDepth)));

            System.out.println("预期委托买价：" + expectBuy100Price);
            System.out.println("预期委托卖价：" + expectSell100Price);
            System.out.println("最新的委托买单信息：" + orderBook.getBids());
            System.out.println("最新的委托卖单信息：" + orderBook.getAsks());

            System.out.println("buys.size()=" + orderBook.getBids().size() + ",sells.size()=" + orderBook.getAsks().size());
            if (orderBook.getBids() == null || orderBook.getBids().size() < orderDepth) {
                System.out.println("buys买单长度不够，进入深度。。。");
                int diff = orderDepth - orderBook.getBids().size();
                BigDecimal diffPrice = new BigDecimal(orderBook.getBids().get(34).getPrice());
                doOpBuyLimit(diff, diffPrice);
            } else {
                BigDecimal singleBuyPrice = new BigDecimal(orderBook.getBids().get(34).getPrice());
                BigDecimal diffUnit = expectBuy100Price.subtract(singleBuyPrice);
                System.out.println("buy>=35,但是价格不相等，相差：" + diffUnit);
                if (diffUnit.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal divideNum = diffUnit.divide(unitPrice);
                    System.out.println("buy>=35,但是价格不相等，divideNum：" + divideNum + ",twentyBuyPrice【上次深度第20档买价格】=" + twentyBuyPrice + "，currentPrice=" + currentPrice);
                    //第20档的价格是否>最新价格（买单）
                    if (divideNum.compareTo(new BigDecimal(10)) > 0 && twentyBuyPrice.compareTo(currentPrice) > 0) {
                        doOpBuyLimit();
                    }
                }
            }

            if (orderBook.getAsks() == null || orderBook.getAsks().size() < orderDepth) {
                System.out.println("sells卖单长度不够，进入深度。。。");
                int diff = orderDepth - orderBook.getAsks().size();
                BigDecimal diffPrice = new BigDecimal(orderBook.getAsks().get(0).getPrice());
                doOpSellLimit(diff, diffPrice);
            } else {
                BigDecimal singleSellPrice = new BigDecimal(orderBook.getAsks().get(34).getPrice());
                BigDecimal diffUnit = singleSellPrice.subtract(expectSell100Price);
                System.out.println("sell>=35,但是价格不相等，相差：" + diffUnit+ ",twentySellPrice【上次深度第20档卖价格】=" + twentySellPrice + "，currentPrice=" + currentPrice);

                if (diffUnit.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal divideNum = diffUnit.divide(unitPrice);
                    System.out.println("sell>=35,但是价格不相等，divideNum：" + divideNum);
                    if (divideNum.compareTo(new BigDecimal(10)) > 0 && twentySellPrice.compareTo(currentPrice) < 0) {
                        doOpSellLimit();
                    }
                }
            }
            System.out.println("深度处理结束*******");

            Thread.sleep(10000l);
        }
    }

    public void doOpBuyLimit(int diff, BigDecimal price) {
        System.out.println("进入doOpBuyLimit，diff:" + diff + ",price:" + price);
        for (int i = 1; i <= diff; i++) {
            BigDecimal buyTradePrice = price.subtract(unitPrice.multiply(new BigDecimal(i)));
            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, tradeAmount() + "", buyTradePrice.toPlainString()));
            System.out.println("判断差" + i + ",深度连续挂单【买】第" + i + "单,返回信息:" + buyResponse);
        }
    }

    public void doOpSellLimit(int diff, BigDecimal price) {
        System.out.println("进入doOpSellLimit，diff:" + diff + ",price:" + price);
        for (int i = 1; i <= diff; i++) {
            BigDecimal sellTradePrice = price.add(unitPrice.multiply(new BigDecimal(i)));
            BigDecimal amount = tradeAmount();
            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, amount + "", sellTradePrice.toPlainString()));
            System.out.println("判断差" + i + ",深度连续挂单【卖】第" + i + "单,返回信息:" + sellResponse);
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
            BigDecimal amount = tradeAmount();
            NewOrderResponse buyResponse = bHexApiRestClient.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, amount + "", buyTradePrice.toPlainString()));
            System.out.println("深度连续挂单【买】第" + i + "单,返回信息:" + buyResponse);
            if (i == 20) {
                this.twentyBuyPrice = buyTradePrice;
            }
        }
        System.out.println("深度连续挂单【买】35单,耗时:" + (System.currentTimeMillis() - sTime));
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

            BigDecimal amount = tradeAmount();
            NewOrderResponse sellResponse = bHexApiRestClient.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, amount + "", sellTradePrice.toPlainString()));
            System.out.println("深度连续挂单【卖】第" + i + "单,返回信息:" + sellResponse);
            if (i == 20) {
                this.twentySellPrice = sellTradePrice;
            }
        }

        System.out.println("深度连续挂单【卖】35单,耗时:" + (System.currentTimeMillis() - sTime));
        System.out.println("卖挂单结束*******");
    }



    private static BigDecimal tradeAmount(){
        return new BigDecimal(autoTradeInitAmount + new Random().nextInt(autoTradeMaxAmount)).divide(new BigDecimal(10));
    }


}
