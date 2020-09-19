package com.kevin.jackson.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kevin.jackson.util.OptimizedBooleanDeserializer;
import lombok.ToString;

import java.io.IOException;

/**
 * @author kevin
 */
public class JsonDeserializeDemo {
    @ToString
    public static class PersonDeserialize {

        public long id = 0;
        public String name = null;

        // 用于为Java对象中给定的属性指定自定义反序列化器类。
        @JsonDeserialize(using = OptimizedBooleanDeserializer.class)
        public boolean enabled = false;
    }

    public static void main(String[] args) throws IOException {
        String json = "{\n  \"id\": 123,\n  \"name\": \"kevin\",\n  \"enabled\": 1\n}";
        ObjectMapper objectMapper = new ObjectMapper();
        PersonDeserialize value = objectMapper.readValue(json, PersonDeserialize.class);
        System.out.println(value);
    }


}
