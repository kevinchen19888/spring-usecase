package com.kevin.jackson.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author kevin
 */
public class OptimizedBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        if ("0".equals(text)) return false;
        return true;
    }
}

