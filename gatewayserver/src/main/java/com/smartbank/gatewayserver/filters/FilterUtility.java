package com.smartbank.gatewayserver.filters;

import org.springframework.stereotype.Component;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {
    public static final String CORRELATION_ID = "smartBank-correlation-id";

    public String getCorrelationId(ServerWebExchange exchange) {
        HttpHeaders requestHeader = exchange.getRequest().getHeaders();
        String correlationId = requestHeader.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(CORRELATION_ID))
                .map(entry -> entry.getValue().get(0))
                .findFirst()
                .orElse(null);
        return correlationId;
    }
}
