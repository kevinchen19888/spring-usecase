package com.kevin.jackson.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kevin.jackson.domain.Car;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

/**
 * 要使用Jackson将JSON读取到JsonNode中，首先需要创建一个Jackson ObjectMapper实例
 *
 * @author kevin
 */
public class JsonNodeUseCase {

    @Test
    public void jsonNodeDemo() {
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //JsonNode jsonNode = objectMapper.readValue(carJson, JsonNode.class);
            JsonNode jsonNode = objectMapper.readTree(carJson);
            System.out.println(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonNodeDemo2() {
        String carJson =
                "{ \"brand\" : \"Mercedes\", \"doors\" : 5," +
                        "  \"owners\" : [\"John\", \"Jack\", \"Jill\"]," +
                        "  \"nestedObject\" : { \"field\" : \"value\" } }";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readValue(carJson, JsonNode.class);

            JsonNode brandNode = jsonNode.get("brand");
            String brand = brandNode.asText();
            System.out.println("brand = " + brand);

            JsonNode doorsNode = jsonNode.get("doors");
            int doors = doorsNode.asInt();
            System.out.println("doors = " + doors);

            JsonNode array = jsonNode.get("owners");
            JsonNode jsonNode2 = array.get(0);
            String john = jsonNode2.asText();
            System.out.println("john  = " + john);

            JsonNode child = jsonNode.get("nestedObject");
            JsonNode childField = child.get("field");
            String field = childField.asText();
            System.out.println("field = " + field);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pojoToJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();

        Car car = new Car();
        car.setBrand("Cadillac");
        car.setDoors(4);
        JsonNode jsonNode = objectMapper.valueToTree(car);
        System.out.println(jsonNode.get("brand").asText());
    }

    @Test
    public void jsonNodeToPojo() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";

        JsonNode carJsonNode = objectMapper.readTree(carJson);

        // 可以直接转为Car对象而无需使用JsonNode,此处仅为示例
        Car car = objectMapper.treeToValue(carJsonNode, Car.class);
        System.out.println(car);
    }

    @Test
    public void jsonNodeToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{ \"f1\" : \"v1\" } ";
        JsonNode jsonNode = objectMapper.readTree(json);

        String newJson = objectMapper.writeValueAsString(jsonNode);
        System.out.println(newJson);
    }

    @Test // 在路径中获取JsonNode字段
    public void findJsonNodeByAt() throws JsonProcessingException {
        String json = "{\"identification\":{\"name\" : \"James\",\"ssn\": \"ABC123552\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        // 通过at方法以给定JsonNode为根的任何位置访问JSON字段
        JsonNode nameNode = jsonNode.at("/identification/name");// /identification/name (json路径表达式)
        System.out.println(nameNode.asText());
    }

    @Test // 转换JsonNode字段
    public void findJsonNode() throws JsonProcessingException {
        String json = "{\"field1\" : \"value1\",\"f2\" : 999}";
        JsonNode jsonNode = new ObjectMapper().readTree(json);
        String f2Str = jsonNode.get("f2").asText();
        double f2Dbl = jsonNode.get("f2").asDouble();
        int f2Int = jsonNode.get("f2").asInt();
        long f2Lng = jsonNode.get("f2").asLong(100);
        System.out.println(String.format("p1:%s,p2:%s,p3:%s,p4:%s", f2Str, f2Dbl, f2Int, f2Lng));
    }

    @Test
    public void objectNode() throws JsonProcessingException {
        String json = "{\"field1\" : \"value1\",\"f2\" : 999}";
        // 创建ObjectNode
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode parentNode = objectMapper.createObjectNode();

        JsonNode childNode = objectMapper.readTree(json);
        // 设置ObjectNode
        parentNode.set("child1", childNode);
        // Put ObjectNode字段
        parentNode.put("field1", "value1");
        parentNode.put("field2", 123);
        parentNode.put("field3", 999.999);

        // 删除字段
        //parentNode.remove("field1");

        // 循环JsonNode字段
        Iterator<String> fieldNames = parentNode.fieldNames();
        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = parentNode.get(fieldName);
            System.out.println(field);
        }

        //System.out.println(parentNode);
    }

}
