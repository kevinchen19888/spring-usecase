package com.kevin.jackson.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.jackson.domain.PersonIgnoreType;

/**
 * @author kevin
 */
public class JsonIgnoreTypeUseCase {

    public static void main(String[] args) throws JsonProcessingException {
        PersonIgnoreType ignoreType = new PersonIgnoreType();
        ignoreType.setName("kevin");
        ignoreType.setPersonId(1000);
        PersonIgnoreType.Address address = new PersonIgnoreType.Address();
        address.setCity("ShenZhen");
        address.setCountry("china");
        address.setHouseNumber("19888");
        ignoreType.setAddress(address);

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(ignoreType);
        System.out.println(s);
    }
}
