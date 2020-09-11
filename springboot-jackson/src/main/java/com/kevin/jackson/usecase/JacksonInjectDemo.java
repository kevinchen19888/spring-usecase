package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.base.ResourceUtil;
import lombok.ToString;

import java.io.IOException;

/**
 * @author kevin
 * </pre>@JacksonInject用于将值注入到解析的对象中<pre/>
 */
public class JacksonInjectDemo {

    @ToString
    public static class PersonInject {

        public long id = 0;
        public String name = null;

        @JacksonInject
        public String source = null;
    }

    public static void main(String[] args) throws IOException {
        InjectableValues inject = new InjectableValues.Std().addValue(String.class, "jenkov.com")
                .addValue("name", "nameVal");
        PersonInject personInject = new ObjectMapper().reader(inject)
                .forType(PersonInject.class)
                .readValue(ResourceUtil.getResourceAsStream("data/person.json"));

        System.out.println(personInject);
    }
}
