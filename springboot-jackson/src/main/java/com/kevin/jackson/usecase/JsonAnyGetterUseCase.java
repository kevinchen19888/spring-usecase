package com.kevin.jackson.usecase;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Jackson将从@JsonAnyGetter注解的方法中获取返回的Map，
 * 并将该Map中的每个键值对都视为一个属性
 *
 * @author kevin
 */
public class JsonAnyGetterUseCase {
    @Setter
    @ToString
    public static class PersonAnyGetter {
        private Map<String, Object> properties = new HashMap<>();

        @JsonAnyGetter
        public Map<String, Object> properties() {
            return properties;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        PersonAnyGetter anyGetter = new PersonAnyGetter();
        HashMap<String, Object> map = new HashMap<>();
        map.put("age", 10);
        map.put("name", "kevin");
        anyGetter.setProperties(map);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(anyGetter));
    }

}
