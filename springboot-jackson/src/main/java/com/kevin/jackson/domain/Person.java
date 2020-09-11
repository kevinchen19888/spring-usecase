package com.kevin.jackson.domain;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author kevin
 */
@Data
public class Person {
    @JsonSetter("id") // 指示Jackson为给定的JSON字段使用对应的setter方法
    private long personId = 0;
    private String name = null;


    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"id\": 1234,\"name\" : \"John\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Person person = objectMapper.readValue(json, Person.class);
        System.out.println(person);
    }
}

