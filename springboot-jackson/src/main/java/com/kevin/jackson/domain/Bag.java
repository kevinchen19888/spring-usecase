package com.kevin.jackson.domain;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 * @author kevin
 */
@ToString
public class Bag {

    private int id;
    private Map<String, Object> properties = new HashMap<>();

    @JsonAnySetter // 如果无此注解则会抛UnrecognizedPropertyException异常
    public void set(String fieldName, Object value) {
        this.properties.put(fieldName, value);
    }

    public Object get(String fieldName) {
        return this.properties.get(fieldName);
    }

    //public void setId(int id) {
    //    this.id = id;
    //}
}

