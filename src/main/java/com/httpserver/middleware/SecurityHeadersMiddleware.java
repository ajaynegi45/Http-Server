package com.httpserver.middleware;

import com.httpserver.http.HttpResponse;

/**
 * Middleware that applies standard security headers to HTTP responses.
 */
public class SecurityHeadersMiddleware implements Middleware {

    /**
     * Applies security headers to the HTTP response, including:
     * - Content-Security-Policy: Restricts the sources for scripts and styles.
     * - Strict-Transport-Security: Enforces HTTPS usage.
     * - X-Content-Type-Options: Prevents MIME type sniffing.
     *
     * @param response The HttpResponse to which security headers are added.
     */
    @Override
    public void apply(HttpResponse response) {
        response.addHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self'");
        response.addHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        response.addHeader("X-Content-Type-Options", "nosniff");
    }
}
