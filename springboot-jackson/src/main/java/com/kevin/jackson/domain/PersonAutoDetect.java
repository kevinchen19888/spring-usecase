package com.kevin.jackson.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author kevin
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // 告诉Jackson在读写对象时包括非public修饰的属性
public class PersonAutoDetect {

    private long personId = 123;
    public String name = null;
}