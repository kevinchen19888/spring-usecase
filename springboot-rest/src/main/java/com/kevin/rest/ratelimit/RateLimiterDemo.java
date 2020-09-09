package com.kevin.rest.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 基于bucket4j进行请求限速示例
 *
 * @author kevin chen
 */
public class RateLimiterDemo {

    public static void main(String[] args) throws InterruptedException {
        // 限制规则：2s内/12次
        long tokens = 10;
        //Bandwidth limit = Bandwidth.simple(tokens, Duration.ofSeconds(1)).withInitialTokens(0);
        //Bucket bucket = Bucket4j.builder().addLimit(limit).build();
        //bucket.asScheduler().consume(tokens);

        // method2
        Refill refill = Refill.intervally(tokens, Duration.ofSeconds(2));
        Bandwidth limit = Bandwidth.classic(tokens, refill);
        Bucket bucket = Bucket4j.builder().addLimit(limit).build();

        //long capacity = 5;
        //Refill refill = Refill.greedy(capacity, Duration.ofSeconds(1));
        //Bandwidth limit = Bandwidth.classic(capacity, refill);
        //Bucket bucket = Bucket4j.builder().addLimit(limit).build();

        // todo 使用示例
        RateLimiterManager manager = new RateLimiterManager();
        Bucket bucket1 = manager.getBucket("kevin",
                new RateLimit(10, Duration.ofSeconds(2)),
                new RateLimit(60, Duration.ofMinutes(1)));

        for (int i = 0; i < 62; i++) {
            bucket1.asScheduler().consume(1);
            System.out.println(LocalDateTime.now().toString());
        }

    }


}
