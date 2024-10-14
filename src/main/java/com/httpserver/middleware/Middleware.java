package com.httpserver.middleware;

import com.httpserver.http.HttpResponse;

/**
 * Interface for middleware components.
 * Middleware components can modify or inspect HTTP responses.
 */
public interface Middleware {
    /**
     * Applies the middleware logic to modify the HTTP response.
     *
     * @param response The HttpResponse object that will be modified by this middleware.
     */
    void apply(HttpResponse response);
}
