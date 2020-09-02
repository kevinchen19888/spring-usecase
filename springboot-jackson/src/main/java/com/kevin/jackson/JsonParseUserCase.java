package com.kevin.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kevin.jackson.domain.Car;
import com.kevin.jackson.domain.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * json解析示例
 *
 * @author kevin
 */
public class JsonParseUserCase {
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void toCarObject() {
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";

        try {
            Car car = objectMapper.readValue(carJson, Car.class);

            System.out.println("car brand = " + car.getBrand());
            System.out.println("car doors = " + car.getDoors());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stringStreamToCarObject() throws IOException {
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 4 }";
        // 通过Reader实例加载的JSON中读取对象
        Reader reader = new StringReader(carJson);

        Car car = objectMapper.readValue(reader, Car.class);
        System.out.println(car);
    }

    @Test
    public void FileJsonToCarObject() throws IOException {
        // 从文件读取JSON并转换为java对象
        URL resource = this.getClass().getClassLoader().getResource("data/car.json");
        // 通过file读取
        //File file = new File(resource.getPath());
        //Car car = objectMapper.readValue(file, Car.class);

        // 通过url读取
        //URL url = new URL("file:D:/Files/workSpace/spring-usecase/springboot-jackson/src/main/resources/data/car.json");
        //Car car = objectMapper.readValue(url, Car.class);

        // 通过InputStream从JSON读取对象
        //InputStream input = new FileInputStream("D:/Files/workSpace/spring-usecase/springboot-jackson/src/main/resources/data/car.json");
        //Car car = objectMapper.readValue(input, Car.class);

        // 通过二进制字节数组读取
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";
        byte[] bytes = carJson.getBytes(StandardCharsets.UTF_8);
        Car car = objectMapper.readValue(bytes, Car.class);

        System.out.println(car);
    }

    @Test
    public void toCarObjectArr() throws JsonProcessingException {
        // JSON数组字符串-->Java对象数组
        String jsonArray = "[{\"brand\":\"ford\"}, {\"brand\":\"Fiat\"}]";

        Car[] cars2 = objectMapper.readValue(jsonArray, Car[].class);
        System.out.println(Arrays.toString(cars2));
    }

    @Test
    public void toCarObjectList() throws JsonProcessingException {
        // JSON数组字符串-->List
        String jsonArray = "[{\"brand\":\"ford\"}, {\"brand\":\"Fiat\"}]";

        List<Car> cars1 = objectMapper.readValue(jsonArray, new TypeReference<List<Car>>() {
        });
        System.out.println(cars1);
    }

    @Test
    public void jsonToMap() throws JsonProcessingException {
        // JSON字符串-->Map
        String jsonStr = "{\"brand\":\"ford\", \"doors\":5}";

        Map<String, Object> jsonMap = objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });

        System.out.println(jsonMap);
    }

    @Test
    public void jsonConfigureIgnoreField() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 忽略未知的JSON字段,否则抛异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonStr = "{\"brand\":\"ford\", \"doors\":5,\"name\":\"kevin\"}";
        Car car = objectMapper.readValue(jsonStr, Car.class);
        System.out.println(car);
    }

    /**
     * 默认情况下，Jackson ObjectMapper会忽略原始字段的空值。
     * 但是，可以将Jackson ObjectMapper配置设置为失败。
     *
     * @throws JsonProcessingException
     */
    @Test
    public void jsonNotAllowedNull() throws JsonProcessingException {
        String json = "{ \"brand\":\"Toyota\", \"doors\":null }";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        Car car = objectMapper.readValue(json, Car.class);
        System.out.println(car);
    }

    /**
     * 自定义反序列化器
     *
     * @throws JsonProcessingException
     */
    @Test
    public void jsonCustomDeserialization() throws JsonProcessingException {
        String json = "{ \"brand\" : \"Ford\", \"doors\" : 6 }";

        SimpleModule module = new SimpleModule("CarDeserializer",
                new Version(3, 1, 8, null, null, null));
        module.addDeserializer(Car.class, new CarDeserializer(Car.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);

        Car car = mapper.readValue(json, Car.class);
        System.out.println(car);
    }

    @Test
    public void jsonWriteVal() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Car car = new Car();
        car.setBrand("BMW");
        car.setDoors(4);

        //URL url = this.getClass().getClassLoader().getResource("data/output-2.json");
        //objectMapper.writeValue(new FileOutputStream(url.getPath()), car);

        String json = objectMapper.writeValueAsString(car);
        System.out.println(json);
    }

    /**
     * 自定义序列化器
     * 如:在JSON中使用与Java对象中不同的字段名称，或者希望完全省略某些字段
     *
     * @throws JsonProcessingException
     */
    @Test
    public void jsonCustomSerialization() throws JsonProcessingException {
        CarSerializer carSerializer = new CarSerializer(Car.class);
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule("CarSerializer",
                new Version(2, 1, 3, null, null, null));
        module.addSerializer(Car.class, carSerializer);

        objectMapper.registerModule(module);

        Car car = new Car();
        car.setBrand("Mercedes");
        car.setDoors(5);

        String carJson = objectMapper.writeValueAsString(car);
        System.out.println(carJson);
    }

    /**
     * 默认的Jackson日期格式，该格式将Date序列化为自1970年1月1日以来的毫秒数（long类型）
     *
     * @throws JsonProcessingException
     */
    @Test
    public void jsonSerializeDate() throws JsonProcessingException {
        Transaction transaction = new Transaction("transfer", new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String output = objectMapper.writeValueAsString(transaction);
        System.out.println(output);
    }

    @Test
    public void testJsonFormat() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        LocalDate date = LocalDate.of(2018, 5, 5);
        String dateStr = mapper.writeValueAsString(date);
        Assert.assertEquals("\"2018-05-05\"", dateStr);

        LocalDateTime dateTime = LocalDateTime.of(2018, 5, 5, 1, 1, 1);
        Assert.assertEquals("\"2018-05-05T01:01:01\"", mapper.writeValueAsString(dateTime));
    }


}
