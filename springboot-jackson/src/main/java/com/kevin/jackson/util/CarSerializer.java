package com.kevin.jackson.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kevin.jackson.domain.Car;

import java.io.IOException;

/**
 * @author kevin
 */
public class CarSerializer extends StdSerializer<Car> {

    public CarSerializer(Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(Car car, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("producer", car.getBrand());
        jsonGenerator.writeNumberField("doorCount", car.getDoors());
        jsonGenerator.writeEndObject();
    }

    public static void main(String[] args) {
        System.out.println("first commit");
        System.out.println("second commit");
        System.out.println("third commit");
    }
}

