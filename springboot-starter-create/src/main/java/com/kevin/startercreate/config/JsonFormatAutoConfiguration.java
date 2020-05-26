package com.kevin.startercreate.config;

import com.kevin.startercreate.FormatTemplate;
import com.kevin.startercreate.processor.FastJsonFormatProcessor;
import com.kevin.startercreate.processor.FormatProcessor;
import com.kevin.startercreate.processor.GsonFormatProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = JsonFormatConfiguration.class)
@EnableConfigurationProperties(value = FormatProperties.class)
@Configuration
public class JsonFormatAutoConfiguration {

    /**
     * @param properties
     * @param formatProcessor
     * @return 如果没有配置默认使用fastjson
     */
    @Bean
    public FormatTemplate formatTemplate(FormatProperties properties, FormatProcessor formatProcessor) {
        if ("fastjson".equals(properties.getType())) {
            return new FormatTemplate(new FastJsonFormatProcessor());
        }
        if ("gson".equals(properties.getType())) {
            return new FormatTemplate(new GsonFormatProcessor());
        }
        return new FormatTemplate(formatProcessor);
    }
}
