package com.owen.bithu.trade.api.huobi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

public class ApiSignature {
    final Logger log = LoggerFactory.getLogger(this.getClass());
    static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
    static final ZoneId ZONE_GMT = ZoneId.of("Z");

    public ApiSignature() {
    }

    public void createSignature(String appKey, String appSecretKey, String method, String host, String uri, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(method.toUpperCase()).append('\n').append(host.toLowerCase()).append('\n').append(uri).append('\n');
        params.remove("Signature");
        params.put("AccessKeyId", appKey);
        params.put("SignatureVersion", "2");
        params.put("SignatureMethod", "HmacSHA256");
        params.put("Timestamp", this.gmtNow());
        SortedMap<String, String> map = new TreeMap(params);
        Iterator var9 = map.entrySet().iterator();

        String actualSign;
        while (var9.hasNext()) {
            Entry<String, String> entry = (Entry) var9.next();
            String key = (String) entry.getKey();
            actualSign = (String) entry.getValue();
            sb.append(key).append('=').append(urlEncode(actualSign)).append('&');
        }

        sb.deleteCharAt(sb.length() - 1);
        var9 = null;

        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secKey = new SecretKeySpec(appSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException var15) {
            throw new RuntimeException("No such algorithm: " + var15.getMessage());
        } catch (InvalidKeyException var16) {
            throw new RuntimeException("Invalid key: " + var16.getMessage());
        }

        String payload = sb.toString();
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        actualSign = Base64.getEncoder().encodeToString(hash);
        params.put("Signature", actualSign);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Dump parameters:");
            Iterator var13 = params.entrySet().iterator();

            while (var13.hasNext()) {
                Entry<String, Object> entry = (Entry) var13.next();
                this.log.debug("  key: " + (String) entry.getKey() + ", value: " + entry.getValue());
            }
        }

    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException var2) {
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }

    long epochNow() {
        return Instant.now().getEpochSecond();
    }

    String gmtNow() {
        return Instant.ofEpochSecond(this.epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
    }
}
