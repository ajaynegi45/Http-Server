package com.httpserver.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    private static final Dotenv dotenv = Dotenv.load();

    private static final int maxRequests = Integer.parseInt(dotenv.get("RATE_LIMITER_MAX_REQUESTS"));
    private static final long timeWindowMs = Long.parseLong(dotenv.get("RATE_LIMITER_TIME_WINDOW_MS"));

    @Bean
    public static int getMaxRequests() {
        return maxRequests;
    }

    @Bean
    public static long getTimeWindowMs() {
        return timeWindowMs;
    }
}
