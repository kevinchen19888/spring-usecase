package com.kevin.rest.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucketBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kevin chen
 */
public class RateLimiterManager {
    private final Map<String, Bucket> rateLimiterMap = new ConcurrentHashMap<>(1024);

    /**
     * @param key    示例:exchangeName+methodName+apiKey
     * @param limits 限制规则对象列表（不能为null）
     * @return 用于进行请求阻塞限制的Bucket实例
     */
    public Bucket getBucket(String key, RateLimit... limits) {
        if (!this.rateLimiterMap.containsKey(key)) {
            LocalBucketBuilder builder;

            if (limits != null) {
                builder = Bucket4j.builder();
                for (RateLimit limit : limits) {
                    builder.addLimit(Bandwidth.classic(limit.getCapacity(), Refill.intervally(limit.getCapacity(), limit.getPeriod())));
                }
            } else {
                throw new IllegalArgumentException("limits不能为null");
            }
            this.rateLimiterMap.putIfAbsent(key, builder.build());
        }
        return this.rateLimiterMap.get(key);
    }

}
