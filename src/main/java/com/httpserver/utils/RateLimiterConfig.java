package com.httpserver.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for rate limiting.
 * This class loads rate limiting settings from environment variables
 * and provides them as Spring beans for dependency injection.
 */
@Configuration
public class RateLimiterConfig {

    // Load environment variables using Dotenv
    private static final Dotenv dotenv = Dotenv.load();

    // Maximum number of requests allowed within the time window
    private static final int maxRequests = Integer.parseInt(dotenv.get("RATE_LIMITER_MAX_REQUESTS"));

    // Time window duration in milliseconds for the rate limiter
    private static final long timeWindowMs = Long.parseLong(dotenv.get("RATE_LIMITER_TIME_WINDOW_MS"));

    /**
     * Provides the maximum number of requests allowed.
     *
     * @return the maximum number of requests
     */
    @Bean
    public static int getMaxRequests() {
        return maxRequests; // Return the configured max requests from environment variables
    }

    /**
     * Provides the time window duration for rate limiting.
     *
     * @return the duration of the time window in milliseconds
     */
    @Bean
    public static long getTimeWindowMs() {
        return timeWindowMs; // Return the configured time window duration
    }
}
