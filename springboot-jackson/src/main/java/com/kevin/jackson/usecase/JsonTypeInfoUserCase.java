package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * 基于@JsonTypeInfo注解处理多态类型的序列化&反序列化
 */
public class JsonTypeInfoUserCase {

    @Data
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")// name属性指定了json序列化&反序列化时多态的判断
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Cat.class, name = "sub1"),
            @JsonSubTypes.Type(value = Dog.class, name = "sub2")})
    static class Animal {
        //String name;
        private int age;
    }

    @Data
    static class Cat extends Animal {
        private String hobby;
    }

    @Data
    //@JsonTypeName(value = "sub1") // 与父类指定@JsonSubTypes.Type 的name属性值作用相同
    static class Dog extends Animal {
        private String tail;
    }

    public static void main(String[] args) throws JsonProcessingException {
        Cat cat = new Cat();
        cat.setHobby("swimming");

        Dog dog = new Dog();
        dog.setTail("grey");

        ObjectMapper mapper = new ObjectMapper();
        String catString = mapper.writeValueAsString(cat);
        String dogString = mapper.writeValueAsString(dog);
        System.out.println(catString);
        System.out.println(dogString);

        // 序列化时根据json中name字段的属性值来进行多态判定
        Animal animal = mapper.readValue(catString, Animal.class);
        Animal animal1 = mapper.readValue(dogString, Animal.class);
        System.out.println(animal);
        System.out.println(animal1);
    }

}
