package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

/**
 * @author kevin
 */
public class JsonValueUseCase {
    @Data
    @Builder
    public static class PersonValue {
        public long personId;
        public String name;

        @JsonValue // 将此方法返回值最为最终对象序列化后的json值,注意:对象返回的值字符串中的所有引号均会转义
        public String toJson() {
            return this.personId + "," + this.name;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        PersonValue value = PersonValue.builder()
                .personId(100)
                .name("kevin").build();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(value)); // output:"100,kevin"
    }

}
