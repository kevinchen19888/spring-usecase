package com.kevin.rest;

import com.kevin.rest.config.RestTemplateRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author kevin chen
 */
@SpringBootApplication
//@Import(value = {RestTemplateRegister.class})
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args).close();// TODO: 2020/7/17 暂时设置服务启动后关闭
    }
}
