package com.kevin.jackson.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author kevin
 */
@Data
public class PersonIgnore {
    @JsonIgnore // 不会从JSON读取或写入JSON属性personId
    public long personId = 0;

    public String name = null;

    public static void main(String[] args) throws JsonProcessingException {
        String carJson = "{ \"personId\" : \"100\", \"name\" : \"kevin\" }";

        ObjectMapper objectMapper = new ObjectMapper();
        PersonIgnore personIgnore = objectMapper.readValue(carJson, PersonIgnore.class);
        System.out.println(personIgnore);
    }

}



