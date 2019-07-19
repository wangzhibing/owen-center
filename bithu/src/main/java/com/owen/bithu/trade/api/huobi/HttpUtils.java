package com.owen.bithu.trade.api.huobi;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private static Proxy proxy;
    private static final String server = "https://api.huobipro.com";
    static OkHttpClient okHttpClient;

    public HttpUtils() {
    }

    public static void setProxy(Proxy proxy) {
        proxy = proxy;
    }

    public static String sendGet(String url, String param, String auth) {
        String result = "";
        BufferedReader in = null;

        try {
            String urlNameString = param == null ? url : url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = null;
            if (proxy != null) {
                connection = realUrl.openConnection(proxy);
            } else {
                connection = realUrl.openConnection();
            }

            connection.setReadTimeout(3000);
            if (auth != null) {
                connection.setRequestProperty("AuthData", auth);
            }

            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();

            String line;
            for (in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")); (line = in.readLine()) != null; result = result + line) {
                ;
            }
        } catch (Exception var16) {
            log.error("发送get异常", var16);
            throw new RuntimeException(var16);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }

        }

        return result;
    }

    public static String sendGetOk(String url, String param, String auth) {
        try {
            String urlNameString = param == null ? url : url + "?" + param;
            Request request = (new Builder()).url(urlNameString).header("Content-type", "application/json").header("accept", "*/*").header("connection", "Keep-Alive").header("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)").build();
            Response response = okHttpClient.newCall(request).execute();
            String content = response.body().string();
            if (!response.isSuccessful()) {
                throw new RuntimeException("huobi api fail code " + response.code() + " body " + content);
            } else {
                return content;
            }
        } catch (Exception var7) {
            log.error("发送get异常", var7);
            throw new RuntimeException(var7);
        }
    }

    public static String sendPost(String url, String param, String body, String auth) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(param == null ? url : url + "?" + param);
            URLConnection conn = null;
            if (proxy != null) {
                conn = realUrl.openConnection(proxy);
            } else {
                conn = realUrl.openConnection();
            }

            conn.setReadTimeout(1000);
            if (null != auth) {
                conn.getHeaderFields().put("AuthData", Arrays.asList(new String[]{auth}));
            }

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json;");
            conn.setRequestProperty("Accept-Language", "Keep-Alive");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(body);
            out.flush();

            String line;
            for (in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); (line = in.readLine()) != null; result = result + line) {
                ;
            }
        } catch (Exception var17) {
            log.error("发送 post 异常", var17);
            throw new RuntimeException(var17);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

        return result;
    }

    public static String sendPostOk(String url, String param, String body, String auth) {
        url = param == null ? url : url + "?" + param;
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), body);
        Builder requestBuilder = (new Builder()).url(url).header("accept", "*/*").header("Content-Type", "application/json;").header("Accept-Language", "Keep-Alive").header("connection", "Keep-Alive").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36").post(requestBody);

        try {
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            String content = response.body().string();
            if (!response.isSuccessful()) {
                throw new RuntimeException("huobi api post fail http code " + response.code() + " http body " + content);
            } else {
                return content;
            }
        } catch (IOException var8) {
            throw new RuntimeException(var8);
        }
    }

    public static JSONObject sendJSONGet(String url, String param, String auth) {
        String res = sendGetOk(url.startsWith("/") ? "https://api.huobipro.com" + url : url, param, auth);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (jsonObject == null) {
            log.error("can not recognize resp {}", res);
        }

        return jsonObject;
    }

    public static JSONObject sendJSONPost(String url, String param, String body, String auth) {
        String res = sendPostOk(url.startsWith("/") ? "https://api.huobipro.com" + url : url, param, body, auth);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (jsonObject == null) {
            log.error("can not recognize resp {}", res);
        }

        return jsonObject;
    }

    static {
        try {
            //Properties properties = PropertiesLoaderUtils.loadAllProperties("key.properties");
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            String enabled = (String) properties.get("proxy.enable");
//            if (enabled.equals("true")) {
//                builder.proxy(new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)));
//                builder.readTimeout(20L, TimeUnit.SECONDS);
//            }

            builder.proxy(new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)));
            builder.readTimeout(20L, TimeUnit.SECONDS);

            okHttpClient = builder.build();
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
}
