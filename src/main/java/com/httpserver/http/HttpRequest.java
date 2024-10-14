package com.httpserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.httpserver.exception.HttpParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.httpserver.utils.RateLimiterConfig;

/**
 * Represents an HTTP request message, including the method, target, HTTP version,
 * headers, and body.
 */
public class HttpRequest extends HttpMessage {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    // Maximum number of requests allowed and the time window (in milliseconds) for rate limiting
    private static final int MAX_REQUESTS = RateLimiterConfig.getMaxRequests(); // Read from env
    private static final long TIME_WINDOW_MS = RateLimiterConfig.getTimeWindowMs(); // Read from env

    private HttpMethod method; // The HTTP method (GET, POST, etc.)
    private String requestTarget; // The target of the request (URL path)
    private String originalHttpVersion; // The HTTP version from the request
    private HttpVersion bestCompatibleHttpVersion; // The best compatible HTTP version
    private final Map<String, String> headers = new HashMap<>(); // To store headers
    private String body; // To store the body of the request
    private final String traceId; // Unique trace ID for tracking the request
    private final String requestId; // Unique request ID for tracking the request

    // Rate limiter properties
    private static final Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>(); // Store rate limiters for clients

    /**
     * Default constructor for HttpRequest. Initializes trace ID and request ID.
     */
    HttpRequest() {
        this.traceId = UUID.randomUUID().toString(); // Generate a unique trace ID
        this.requestId = UUID.randomUUID().toString(); // Generate a unique request ID
        logger.debug("HttpRequest object created with trace ID: {} and request ID: {}", traceId, requestId);
        logger.trace("HttpRequest constructor completed for trace ID: {} and request ID: {}", traceId, requestId);
    }

    // Rate limiter logic
    /**
     * Checks if a request is allowed for the given client ID based on the rate limiter.
     *
     * @param clientId The identifier for the client making the request.
     * @return true if the request is allowed; false otherwise.
     */
    public boolean isRequestAllowed(String clientId) {
        // Create a new rate limiter for the client if it doesn't already exist
        rateLimiters.putIfAbsent(clientId, new RateLimiter(MAX_REQUESTS, TIME_WINDOW_MS));
        RateLimiter rateLimiter = rateLimiters.get(clientId); // Retrieve the rate limiter for this client
        boolean allowed = rateLimiter.allowRequest(); // Check if the request is allowed
        logger.trace("Rate limiter checked for clientId {}. Request allowed: {} for trace ID: {} and request ID: {}", clientId, allowed, traceId, requestId);
        return allowed; // Return the result of the rate limiter check
    }

    // Getters and setters

    public HttpMethod getMethod() {
        return method; // Return the HTTP method
    }

    public String getRequestTarget() {
        return requestTarget; // Return the request target
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion; // Return the original HTTP version
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion; // Return the best compatible HTTP version
    }

    public Map<String, String> getHeaders() {
        return headers; // Return the headers map
    }

    public String getBody() {
        return body; // Return the body of the request
    }

    /**
     * Gets the trace ID for the request.
     *
     * @return the trace ID.
     */
    public String getTraceId() {
        return traceId; // Return the trace ID
    }

    /**
     * Gets the request ID for the request.
     *
     * @return the request ID.
     */
    public String getRequestId() {
        return requestId; // Return the request ID
    }

    // Update logging messages to include traceId and requestId
    public void setMethod(HttpMethod method) {
        this.method = method; // Set the HTTP method
        logger.debug("HTTP method set to {} for trace ID {} and request ID {}", method, traceId, requestId);
        logger.trace("Method set successfully for trace ID: {} and request ID: {}", traceId, requestId);
    }

    public void setRequestTarget(String requestTarget) throws HttpParsingException {
        logger.trace("Attempting to set request target for trace ID {} and request ID {}", traceId, requestId);
        // Validate request target
        if (requestTarget == null || requestTarget.isEmpty()) {
            logger.error("Invalid request target: '{}' for trace ID {} and request ID {}. Request target must not be null or empty.", requestTarget, traceId, requestId);
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget; // Set the request target
        logger.debug("Request target set to '{}' for trace ID {} and request ID {}", requestTarget, traceId, requestId);
    }

    public void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        logger.trace("Attempting to set HTTP version for trace ID {} and request ID {}", traceId, requestId);
        this.originalHttpVersion = originalHttpVersion; // Set the original HTTP version
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion); // Determine best compatible version
        // Check if the best compatible version is valid
        if (this.bestCompatibleHttpVersion == null) {
            logger.error("HTTP version not supported: '{}' for trace ID {} and request ID {}. Throwing BadHttpVersionException.", originalHttpVersion, traceId, requestId);
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
        logger.debug("Original HTTP version set to '{}', best compatible version: '{}' for trace ID {} and request ID {}", originalHttpVersion, bestCompatibleHttpVersion, traceId, requestId);
    }

    public void addHeader(String name, String value) {
        logger.trace("Attempting to add header for trace ID {} and request ID {}", traceId, requestId);
        // Validate header name and value
        if (name == null || name.isEmpty() || value == null) {
            logger.error("Invalid header name or value: name='{}', value='{}' for trace ID {} and request ID {}. Header name and value must not be null or empty.", name, value, traceId, requestId);
            throw new IllegalArgumentException("Header name and value must not be null or empty");
        }
        headers.put(name, value); // Add the header to the map
        logger.debug("Added header: {} = '{}' for trace ID {} and request ID {}", name, value, traceId, requestId);
    }

    public void setBody(String body) {
        logger.trace("Attempting to set body for trace ID {} and request ID {}", traceId, requestId);
        this.body = body; // Set the body of the request
        logger.debug("Request body set for trace ID {} and request ID {}", traceId, requestId);
    }

    @Override
    public String toString() {
        // Provide a string representation of the HttpRequest object
        return "HttpRequest{" +
                "method=" + method +
                ", requestTarget='" + requestTarget + '\'' +
                ", originalHttpVersion='" + originalHttpVersion + '\'' +
                ", bestCompatibleHttpVersion=" + bestCompatibleHttpVersion +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                ", traceId='" + traceId + '\'' + // Include trace ID in the string representation
                ", requestId='" + requestId + '\'' + // Include request ID in the string representation
                '}';
    }

    // Simple rate limiter class using AtomicInteger
    private static class RateLimiter {
        private final int maxRequests; // Maximum allowed requests
        private final long timeWindow; // Time window in milliseconds
        private long windowStartTime; // Start time of the current time window
        private final AtomicInteger requestCount; // Counter for requests in the current window

        /**
         * Constructs a RateLimiter with the specified maximum number of requests
         * and time window.
         *
         * @param maxRequests Maximum requests allowed.
         * @param timeWindow  Time window in milliseconds.
         */
        RateLimiter(int maxRequests, long timeWindow) {
            this.maxRequests = maxRequests; // Set the maximum requests
            this.timeWindow = timeWindow; // Set the time window
            this.windowStartTime = System.currentTimeMillis(); // Initialize window start time
            this.requestCount = new AtomicInteger(0); // Initialize request count
            logger.trace("RateLimiter initialized with maxRequests {} and timeWindow {}ms", maxRequests, timeWindow);
        }

        /**
         * Allows a new request if it is within the rate limit.
         *
         * @return true if the request is allowed; false otherwise.
         */
        boolean allowRequest() {
            long currentTime = System.currentTimeMillis(); // Get the current time

            // Check if the current time exceeds the time window
            if (currentTime - windowStartTime > timeWindow) {
                logger.trace("Time window expired, resetting request count for RateLimiter");
                // Reset the count for the new time window
                windowStartTime = currentTime; // Reset the window start time
                requestCount.set(1); // Reset count since we're allowing a new request
                return true; // Allow the first request of the new window
            }

            // Allow the request if under the limit
            if (requestCount.get() < maxRequests) {
                requestCount.incrementAndGet(); // Atomic increment
                logger.trace("Request allowed for client, requestCount = {} for RateLimiter", requestCount.get());
                return true; // Allow additional requests within the limit
            }

            // Rate limit exceeded
            logger.warn("Rate limit exceeded for client. Request denied. Client has made {} requests in the current window, max allowed is {}.", requestCount.get(), maxRequests);
            return false; // Deny request
        }
    }
}
