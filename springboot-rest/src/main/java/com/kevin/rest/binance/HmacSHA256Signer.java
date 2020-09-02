package com.kevin.rest.binance;

import org.apache.commons.codec.binary.Hex;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * Utility class to sign messages using HMAC-SHA256.
 */
public class HmacSHA256Signer {
    HttpEntity<MultiValueMap<String, Object>> httpEntity;

    public static void main(String[] args) {
        Map<String, Object> params = new LinkedHashMap<>();
        long timeStamp = System.currentTimeMillis();
        params.put("symbol", "BTCUSDT");
        params.put("side", "BUY");
        params.put("type", "LIMIT");
        params.put("price", "10");
        params.put("quantity", "1");
        params.put("timeInForce", "GTC");
        params.put("timestamp", timeStamp);
        String sin = createSigns(params, "timestamp");
        System.out.println(sin);
    }


    /**
     * Sign the given message using the given secret.
     *
     * @param message message to sign
     * @param secret  secret key
     * @return a signed message
     */
    public static String sign(String message, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            return new String(Hex.encodeHex(sha256_HMAC.doFinal(message.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message.", e);
        }
    }


    /**
     * @param secret   api私钥
     * @param paramMap 参数map
     * @return 通过HmacSHA256算法加密后的签名
     */
    public static String sign(String secret, Map<String, Object> paramMap) {
        try {
            Map<String, String> params = new HashMap<>();
            Set<String> set = paramMap.keySet();
            for (String string : set) {
                if (!paramMap.get(string).equals("")) {
                    params.put(string, String.valueOf(paramMap.get(string)));
                }
            }
            String toSignStr = createSign(params);

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            return new String(Hex.encodeHex(sha256_HMAC.doFinal(toSignStr.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign secret.", e);
        }
    }


    /**
     * 对参数map进行自然排序并进行url拼接
     *
     * @param params        待进行自然排序的参数map
     * @param excludeParams
     * @return 已经排好序并经过url拼接的所有参数字符串
     */
    public static String createSign(Map<String, String> params, String... excludeParams) {
        return getUrl(params, excludeParams);
    }

    public static String createSigns(Map<String, Object> params, String... excludeParams) {
        Map<String, String> map = new HashMap<>();
        for (String key : params.keySet()) {
            map.put(key, String.valueOf(params.get(key)));
        }
        return getUrl(map, excludeParams);
    }

    private static String getUrl(Map<String, String> params, String[] excludeParams) {
        if (excludeParams != null) {
            for (int i = 0; i < excludeParams.length; i++) {
                params.remove(excludeParams[i]);
            }
        }
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuilder temp = new StringBuilder();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key.toString()).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            temp.append(valueString);
        }
        return temp.toString();
    }

}
