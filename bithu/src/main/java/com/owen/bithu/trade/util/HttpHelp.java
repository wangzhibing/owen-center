package com.owen.bithu.trade.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * http请求封装
 * Created by chars on 16/3/31 19:57
 * Copyright © mizhuanglicai
 */
public class HttpHelp {

    //最大连接数
    private static final int MAX_TOTAL_CONNECTION = 20;

    //连接超时时间
    private static final int CONNECTION_TIME_OUT = 1000;

    //响应超时时间
    private static final int READ_TIME_OUT = 5000;

    private static PoolingHttpClientConnectionManager connectionManager;

    private static RequestConfig config;

    static {
        connectionManager = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTION);
        config = RequestConfig.custom().setSocketTimeout(READ_TIME_OUT).setConnectTimeout(CONNECTION_TIME_OUT).build();
    }

    public static CloseableHttpClient getHttpClient() {
        return HttpClients.createMinimal(connectionManager);
    }

    public static HttpPost getPostRequest(String url) {
        HttpPost request = new HttpPost(url);
        request.setConfig(config);
        return request;
    }

    public static HttpGet getGetRequest(String url) {
        HttpGet request = new HttpGet(url);
        request.setConfig(config);
        return request;
    }

    public static HttpResponse execute(HttpRequestBase request) throws IOException {
        HttpClient client = getHttpClient();
        return client.execute(request);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        HttpPost post = HttpHelp.getPostRequest("http://121.43.148.191:8007/services/callback/capitalSuccess");
        String json = "{\"amount\":1,\"status\":\"00000000\",\"tradeNo\":\"20160331000904000000000902309229\",\"accountTradeType\":\"cancel\"}";
        HttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            response = HttpHelp.execute(post);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
                System.out.println(result);
            }
            System.out.println("code" + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response!=null){
                try {
                    EntityUtils.consume(response.getEntity());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

    }
}
