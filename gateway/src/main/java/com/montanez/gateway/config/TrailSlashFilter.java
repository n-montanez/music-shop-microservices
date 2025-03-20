package com.montanez.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TrailSlashFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUri = exchange.getRequest().getURI().toString();

        if (requestUri.endsWith("/") && !requestUri.equals("/")) {
            String newUri = requestUri.substring(0, requestUri.length() - 1);

            exchange.getResponse().setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, newUri);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

}
