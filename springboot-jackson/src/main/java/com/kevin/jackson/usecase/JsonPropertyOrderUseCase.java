package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

/**
 * JsonPropertyOrder Jackson注解可用于指定将Java对象的字段序列化为JSON的顺序
 *
 * @author kevin
 */
public class JsonPropertyOrderUseCase {

    @JsonPropertyOrder({"name", "familyName", "personId"})
    @Data
    @Builder
    public static class PersonPropertyOrder {
        public long personId;
        public String name;
        public String familyName;
    }

    public static void main(String[] args) throws JsonProcessingException {
        PersonPropertyOrder order = PersonPropertyOrder.builder().personId(10)
                .name("kevin")
                .familyName("chen").build();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(order));
    }

}
