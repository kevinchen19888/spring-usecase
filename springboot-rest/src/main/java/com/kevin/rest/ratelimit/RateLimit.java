package com.kevin.rest.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

/**
 * @author kevin chen
 */
@Data
@AllArgsConstructor
public class RateLimit {
    /**
     *
     */
    private long capacity;
    private Duration period;
}
