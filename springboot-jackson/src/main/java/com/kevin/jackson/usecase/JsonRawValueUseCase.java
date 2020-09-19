package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * JsonRawValue Jackson注解告诉Jackson该属性值应直接写入JSON输出。
 * 如果该属性是字符串，Jackson通常会将值括在引号中，
 * 但是如果使用@JsonRawValue属性进行注解，Jackson将不会这样做。
 *
 * @author kevin
 */
public class JsonRawValueUseCase {
    @Data
    public static class PersonRawValue {
        public long personId = 0;
        @JsonRawValue // 序列化为原始值,这对于属性包含json字符串值得情况非常有用;
        public String address = "{\"street\":\"Wall Street\", \"no\":1}";// 最终会被序列化为原始json串,不含转义字符
    }

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"personId\":0,\"address\":\"$#\"}\n";
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(new PersonRawValue()));
    }

}
