package com.kevin.jackson.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Data;

/**
 * @author kevin
 */
@Data
public class PersonIgnoreType {

    @JsonIgnoreType // 用于将整个类型（类）标记为在使用该类型的任何地方都将被忽略
    @Data
    public static class Address {
        public String streetName = null;
        public String houseNumber = null;
        public String zipCode = null;
        public String city = null;
        public String country = null;
    }

    public long personId = 0;

    private String name = null;

    public Address address = null;
}

