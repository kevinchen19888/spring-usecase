package com.kevin.startercreate.config;

import com.kevin.startercreate.processor.FastJsonFormatProcessor;
import com.kevin.startercreate.processor.FormatProcessor;
import com.kevin.startercreate.processor.GsonFormatProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JsonFormatConfiguration {

    @Primary
    @Bean
    @ConditionalOnClass(name = "com.alibaba.fastjson.JSONObject")
    //@ConditionalOnClass(name = "com.alibaba.fastjson.JSON")// 此条件不生效 todo
    public FormatProcessor fastJsonFormat() {
        return new FastJsonFormatProcessor();
    }

    @Bean
    @ConditionalOnClass(name = "com.google.gson.Gson")
    public FormatProcessor gsonFormat() {
        return new GsonFormatProcessor();
    }
}
