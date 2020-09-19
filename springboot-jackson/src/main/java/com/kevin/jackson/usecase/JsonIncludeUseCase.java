package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * JsonInclude 告诉Jackson仅在某些情况下包括属性。
 * 例如，仅当属性为非null，非空或具有非默认值时，才应包括该属性
 *
 * @author kevin
 */
public class JsonIncludeUseCase {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class PersonInclude {
        public long personId = 0;
        public String name = null;
    }

    public static void main(String[] args) throws JsonProcessingException {
        //language=JSON
        String json = "{\n  \"personId\": 10,\n  \"name\": \"\"\n}";
        ObjectMapper mapper = new ObjectMapper();
        PersonInclude value = mapper.readValue(json, PersonInclude.class);
        System.out.println(value);
    }

}
