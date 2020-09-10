package com.kevin.jackson;

import com.kevin.jackson.domain.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kevin
 */
@Configuration
public class DemoConfig {

    @Bean
    public Car car() {
        return new Car();
    }
}
