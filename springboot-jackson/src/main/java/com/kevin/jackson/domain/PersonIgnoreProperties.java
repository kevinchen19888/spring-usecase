package com.kevin.jackson.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author kevin
 */
// 指定要忽略的类的属性列表,放置在类声明上方
@JsonIgnoreProperties({"firstName", "lastName"})
@Data
public class PersonIgnoreProperties {

    public long personId = 0;

    public String firstName = null;
    public String lastName = null;

    public static void main(String[] args) throws JsonProcessingException {
        String carJson = "{ \"personId\" : \"100\", \"firstName\" : \"chen\",\"lastName\" : \"kevin\" }";

        ObjectMapper objectMapper = new ObjectMapper();
        PersonIgnoreProperties value = objectMapper.readValue(carJson, PersonIgnoreProperties.class);
        System.out.println(value);
    }
}
