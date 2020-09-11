package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;

/**
 * JsonCreator注解在无法使用@JsonSetter注解的情况下很有用。
 * 例如，不可变对象没有任何设置方法，因此它们需要将其初始值注入到构造函数中
 *
 * @author kevin
 */
public class JsonCreatorDemo {
    @ToString
    public static class PersonImmutable {
        private long id = 0;
        private String name = null;

        @JsonCreator // 若无此注解则抛InvalidDefinitionException异常
        public PersonImmutable(@JsonProperty("id") long id,
                               @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"id\": 1234,\"name\" : \"John\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        PersonImmutable value = objectMapper.readValue(json, PersonImmutable.class);
        System.out.println(value);
    }

}
