package com.kevin.jackson.usecase;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.kevin.jackson.domain.Car;
import org.junit.Test;

import java.io.IOException;

/**
 * 创建Jackson JsonParser，首先需要创建一个JsonFactory;
 * JsonParser的运行层级低于Jackson ObjectMapper,
 * 这使得JsonParser比ObjectMapper更快，但使用起来也比较麻烦
 * JsonParser的工作方式是将JSON分解为一系列令牌，可以一个一个地迭代令牌
 *
 * @author kevin
 */
public class JsonParserUseCase {

    @Test
    public void createParser() throws IOException {
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";

        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(carJson);

        Car car = new Car();
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();// 返回当前字段名称
                System.out.println(fieldName);

                jsonToken = parser.nextToken();// TODO: 2020/8/25 ?

                if ("brand".equals(fieldName)) {
                    car.setBrand(parser.getValueAsString());
                } else if ("doors".equals(fieldName)) {
                    car.setDoors(parser.getValueAsInt());
                }
            }
        }

        System.out.println("car.brand = " + car.getBrand());
        System.out.println("car.doors = " + car.getDoors());

    }

}
