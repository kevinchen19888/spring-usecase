package com.kevin.rest.binance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author kevin chen
 */
@Component
public class DemoRestTemplateTask implements CommandLineRunner {

    @Autowired
    private DefaultListableBeanFactory factory;

    @Override
    public void run(String... args) throws Exception {
        RestTemplate huobiRestTemplate = (RestTemplate) factory.getBean("huobiRestTemplate");
        System.out.println(huobiRestTemplate.getForObject("https://www.baidu.com",String.class));
    }
}
