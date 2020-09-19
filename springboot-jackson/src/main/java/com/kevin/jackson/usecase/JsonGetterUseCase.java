package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;

/**
 * 用于告诉Jackson，应该通过调用getter方法而不是通过直接字段访问来获取某个字段值。
 * 如果Java类使用jQuery样式的getter和setter名称，则@JsonGetter注解很有用
 *
 * @author kevin
 */
public class JsonGetterUseCase {

    @ToString
    public static class PersonGetter {
        private long personId = 0;

        @JsonGetter("id")
        public long personId() {
            return this.personId;
        }

        @JsonSetter("id")
        public void personId(long personId) {
            this.personId = personId;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"id\":11}\n";
        ObjectMapper mapper = new ObjectMapper();
        PersonGetter value = mapper.readValue(json, PersonGetter.class);
        System.out.println(value);
        System.out.println(mapper.writeValueAsString(value));
    }

}
