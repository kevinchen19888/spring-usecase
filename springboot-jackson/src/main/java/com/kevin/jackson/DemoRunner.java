package com.kevin.jackson;

import com.kevin.jackson.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author kevin
 */
@Component
public class DemoRunner implements ApplicationRunner {
    private ApplicationContext context;

    public DemoRunner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Car bean = context.getBean(Car.class);
        System.out.println(bean);
    }
}
