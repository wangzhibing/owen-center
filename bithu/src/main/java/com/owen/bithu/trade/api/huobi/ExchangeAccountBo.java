package com.owen.bithu.trade.api.huobi;

/**
 * Created by Mizlicai on 2019/7/3.
 * 交易所账号
 */
public class ExchangeAccountBo {

    /**
     * 访问key（api_key）
     */
    private String accessKey;

    /**
     * 密码key
     */
    private String secretKey;

    public ExchangeAccountBo(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public ExchangeAccountBo(String accessKey) {

        this.accessKey = accessKey;
    }

    public String getSecretKey() {

        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

