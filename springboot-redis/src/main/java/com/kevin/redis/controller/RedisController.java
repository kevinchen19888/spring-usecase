package com.kevin.redis.controller;

import com.kevin.redis.entity.User;
import com.kevin.startercreate.FormatTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedisController {
    @Autowired
    private FormatTemplate formatTemplate;

    @GetMapping("/formatTest")
    public String formatTest() {
        User user = User.builder().id(11)
                .name("kevin")
                .nickName("xiaolong")
                .build();
        return formatTemplate.doFormat(user);
    }
}
