package com.kevin.rest.binance;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kevin chen
 */
public class CollectionUtil {

    public static MultiValueMap toMultiValueMap(Map<String, Object> map) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            multiValueMap.add(entry.getKey(), entry.getValue());
        }
        return multiValueMap;
    }

    public static Map<String, Object> multiValueMapToLinkedMap(MultiValueMap<String, Object> map) {
        Map<String, Object> linkedMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            linkedMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return linkedMap;
    }
}
