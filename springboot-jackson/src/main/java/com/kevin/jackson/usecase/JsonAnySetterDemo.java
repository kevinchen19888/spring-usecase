package com.kevin.jackson.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.jackson.domain.Bag;

/**
 * 注解@JsonAnySetter表示Jackson为JSON对象中所有无法识别的字段调用相同的setter方法
 * 如果有则会调用对应属性的setter方法进行赋值
 *
 * @author kevin
 */
public class JsonAnySetterDemo {

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"id\": 1234,\"name\" : \"John\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Bag bag = objectMapper.readValue(json, Bag.class);
        System.out.println(bag);
    }
}
