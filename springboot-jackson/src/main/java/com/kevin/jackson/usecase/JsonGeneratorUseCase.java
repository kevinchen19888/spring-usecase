package com.kevin.jackson.usecase;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * JsonGenerator用于从Java对象（或代码从中生成JSON的任何数据结构）生成JSON
 *
 * @author kevin
 */
public class JsonGeneratorUseCase {

    @Test
    public void creatGenerator() throws IOException {
        // 必须存在此文件/路径,否则url为null
        URL url = this.getClass().getClassLoader().getResource("data/generator.json");
        JsonFactory factory = new JsonFactory();

        JsonGenerator generator = factory.createGenerator(new File(url.getPath()), JsonEncoding.UTF8);
        generator.writeStartObject();
        generator.writeStringField("brand", "Mercedes");
        generator.writeNumberField("doors", 5);
        generator.writeEndObject();

        generator.close();// 需要关闭JsonGenerator
    }

}
