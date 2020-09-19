package com.kevin.jackson.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kevin.jackson.util.BaseModule;
import com.kevin.jackson.util.OptimizedBooleanSerializer;
import lombok.Data;

/**
 * JsonSerialize Jackson注解用于为Java对象中的字段指定自定义序列化程序
 * 有两种方式可以自定义对象属性的序列化规则:
 * 1,直接使用@JsonSerialize并制定序列化器
 * 2,定义一个Module并继承 SimpleModule,然后注册到ObjectMapper中
 *
 * @author kevin
 */
public class JsonSerializeUseCase {
    @Data
    public static class PersonSerializer {
        public long personId = 0;
        public String name = "John";
        //@JsonSerialize(using = OptimizedBooleanSerializer.class) // 第一种方式
        public Boolean enabled = false;
    }

    public static void main(String[] args) throws JsonProcessingException {
        PersonSerializer pe = new PersonSerializer();
        ObjectMapper mapper = new ObjectMapper();
        // 第二种方式,此方式要求属性不能为基本数据类型
        mapper.registerModule(new BaseModule());
        System.out.println(mapper.writeValueAsString(pe));
    }


}
