package com.kevin.startercreate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "json.format")
public class FormatProperties {
    private String type;
}
