package com.owen.bithu.trade.api.zgtop.responseDTO;

/**
 * Created by Mizlicai on 2019/6/13.
 * 用户账户信息
 */
public class ZgBalanceAccountDTO {

    private String symbol;

    private String balance;

    private String name;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {

        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
