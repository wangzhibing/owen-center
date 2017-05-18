package com.owen.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 长链接 key value
 * 
 * @author owen
 */
public class SimpleCurrentMap {

    private static Logger              logger = LoggerFactory.getLogger(SimpleCurrentMap.class.getName());

    private static Map<String, String> map    = new ConcurrentHashMap<String, String>();

    public static void add(String clientId, String value) {
        map.put(clientId, value);
    }

    public static String get(String clientId) {
        logger.info("SimpleNettyChannelMap.get.client={}", clientId);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            logger.info("SimpleCurrentMap.key={},value={}", entry.getKey(), entry.getValue());
        }
        return map.get(clientId);
    }

    public static void remove(String v) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() == v) {
                map.remove(entry.getKey());
            }
        }
    }

}
