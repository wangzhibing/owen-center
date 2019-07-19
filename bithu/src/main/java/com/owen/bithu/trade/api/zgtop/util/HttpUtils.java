package com.owen.bithu.trade.api.zgtop.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.owen.bithu.trade.api.zgtop.ZgtopConstant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Mizlicai on 2019/6/13.
 */
public class HttpUtils {


    public static final String DEFAUTL_ENCODING = "UTF-8";

    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static MultiThreadedHttpConnectionManager connectionManager = null;

    private static int connectionTimeOut = 5000;

    private static int socketTimeOut = 5000;

    private static int maxConnectionPerHost = 10;

    private static int maxTotalConnections = 50;

    private static HttpClient httpClient = null;

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static HttpClient getInstance() {
        if (null == httpClient) {
            System.out.println("HttpClient init null stater ！");
            connectionManager = new MultiThreadedHttpConnectionManager();
            //connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
            //connectionManager.getParams().setSoTimeout(socketTimeOut);
            connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
            connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
            httpClient = new HttpClient(connectionManager);

//            connectionManager = new MultiThreadedHttpConnectionManager();
//            connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
//            connectionManager.getParams().setSoTimeout(socketTimeOut);
//            connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
//            connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
            httpClient = new HttpClient(connectionManager);
            System.out.println("HttpClient初始化完毕！");
        }
        return httpClient;
    }

    public static String sendGetRequest(String uri, Map<String, Object> params) {
        return sendGetRequest(uri, params, DEFAUTL_ENCODING);
    }

    public static String sendGetRequest(String uri, Map<String, Object> params, String encoding) {
        GetMethod getMethod = null;
        String url = "";
        String response = "";

        try {
            StringBuilder paramsBuf = new StringBuilder();
            if (params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    paramsBuf.append(URL_PARAM_CONNECT_FLAG).append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), encoding));
                }
                if (0 > uri.indexOf("\\?")) {
                    url = uri + "?" + paramsBuf.deleteCharAt(0).toString();
                } else {
                    url = uri + paramsBuf.toString();
                }
            } else {
                url = uri;
            }

            getMethod = new GetMethod(url);

           // getMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
            getMethod.setRequestHeader("Accept-Language", "zh-cn");
            getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
            getMethod.setRequestHeader("Accept-Charset", encoding);
            getMethod.setRequestHeader("Keep-Alive", "300");
            getMethod.setRequestHeader("Connection", "Keep-Alive");
            getMethod.setRequestHeader("Cache-Control", "no-cache");

            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);

            int statusCode = getInstance().executeMethod(getMethod);
            if (HttpStatus.SC_OK == statusCode) {
                response = getMethod.getResponseBodyAsString();
            } else {
                System.out.println("发送(" + url + ")请求响 应失败，响应码为：" + statusCode);
            }
        } catch (IOException e) {
            System.out.println("发送(" + url + ")请求响应失败" + e.getMessage());
            e.printStackTrace();

        } finally {
            if (null != getMethod) {
                getMethod.releaseConnection();
            }
        }
        return response;
    }


    public static JSONObject sendGetRequestForJson(String uri, Map<String, Object> params) {
        String response = sendGetRequest(uri, params);
        if (response == null || response.equals("")) {
            return new JSONObject();
        }

        return JSON.parseObject(response);
    }


    public static JSONObject sendPostRequestForJson(String uri, Map<String, Object> params) {
        String response = sendPostRequest(uri, params);
        if (response == null || response.equals("")) {
            return new JSONObject();
        }

        return JSON.parseObject(response);
    }


    public static String sendPostRequest(String uri, Map<String, Object> params) {
        return sendPostRequest(uri, params, DEFAUTL_ENCODING);
    }

    public static String sendPostRequest(String uri, Map<String, Object> params, String encoding){
        String response = "";
        PostMethod postMethod = new PostMethod(uri);

        postMethod.setRequestHeader("Accept-Language", "zh-cn");
        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        postMethod.setRequestHeader("Accept-Charset", encoding);
        postMethod.setRequestHeader("Keep-Alive", "300");
        postMethod.setRequestHeader("Connection", "Keep-Alive");
        postMethod.setRequestHeader("Cache-Control", "no-cache");

        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);

        if(params != null && params.size() > 0){
            for(String key : params.keySet()){
                postMethod.addParameter(key, params.get(key).toString());
            }
        }

        try {
            int statusCode = getInstance().executeMethod(postMethod);
            if(HttpStatus.SC_OK == statusCode){
                response = postMethod.getResponseBodyAsString();
            }else{
                System.out.println("发送(" + uri + ")请求响 应失败，响应码为：" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("发送" + uri + "请求响应失败", e);
        } finally {
            if(null != postMethod){
                postMethod.releaseConnection();
            }
        }
        return response;
    }

    public static String getSignature(Map<String, Object> paramsMap) {
        StringBuilder signBuf = new StringBuilder();
        for (String key : paramsMap.keySet()) {
            if (!key.equals(ZgtopConstant.SIGN_KEY)) {
                signBuf.append(key).append("=").append(paramsMap.get(key)).append("&");
            }
        }
        signBuf.deleteCharAt(signBuf.length() - 1);
        return SignatureUtil.getSign(signBuf.toString());
    }
}
