package com.kevin.rest.binance;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 通过HmacSHA256算法对参数进行签名
 *
 * @author kevin
 */
public final class NewHmacSHA256Signer {



    /**
     * @param secret   api私钥
     * @param paramMap 参数map
     * @return 通过HmacSHA256算法加密后的签名
     */
    public static String sign(String secret, Map<String, Object> paramMap) {
        try {
            String toSignStr = sortParam(paramMap);

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            return new String(Hex.encodeHex(sha256_HMAC.doFinal(toSignStr.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign secret.", e);
        }
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
     * 对参数map进行自然排序并进行url拼接
     *
     * @param paramMap 待进行自然排序的参数map
     * @return 已经排好序并经过url拼接的所有参数字符串
     */
    public static String sortParam(Map<String, Object> paramMap) {

        Map<String, String> params = new HashMap<>();
        Set<String> set = paramMap.keySet();
        for (String string : set) {
            if (!paramMap.get(string).equals("")) {
                params.put(string, String.valueOf(paramMap.get(string)));
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
