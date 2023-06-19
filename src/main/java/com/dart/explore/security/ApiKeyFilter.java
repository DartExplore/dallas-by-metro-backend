package com.dart.explore.security;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "x-api-key";

    private final String apiKey;

    public ApiKeyFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
        } else {
            String requestApiKey = request.getHeader(API_KEY_HEADER);
            if (apiKey != null && apiKey.equals(requestApiKey)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Invalid API Key");
            }
        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> excludedRoutes = Arrays.asList(
                "/api/public",
                "/swagger-ui",
                "/v3/api-docs"
        );
        String servletPath = request.getServletPath();
        return excludedRoutes.stream().anyMatch(servletPath::startsWith);
    }
}
