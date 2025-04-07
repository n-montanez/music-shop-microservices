package com.montanez.gateway.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Whitelist {
    private static final Map<String, List<String>> WHITELIST = new HashMap<>();

    static {
        // Whitelisted POST paths
        WHITELIST.put("POST", List.of(
                "/customer-service/customer",
                "/customer-service/customer/login"));

        // Whitelisted GET paths
        WHITELIST.put("GET", List.of(
                "/api-docs/**",
                "/v3/api-docs/**",
                "/swagger/**",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/customer-service/v3/api-docs/**"));
    }

    public static List<String> getWhitelistedPaths(String method) {
        return WHITELIST.getOrDefault(method, List.of());
    }
}
