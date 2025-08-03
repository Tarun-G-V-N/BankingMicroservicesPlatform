package com.smartbank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = filterUtility.getCorrelationId(exchange);
        if(correlationId != null) {
            logger.debug("smartBank-correlation-id found in RequestTraceFilter: {}", correlationId);
        }
        else {
            correlationId = UUID.randomUUID().toString();
            logger.debug("smartBank-correlation-id generated in RequestTraceFilter: {}", correlationId);
            ServerWebExchange mutatedExchange = updateRequestHeaderWithNewCorrelationId(exchange, correlationId);
            return chain.filter(mutatedExchange);
        }
        return chain.filter(exchange);
    }

    private ServerWebExchange updateRequestHeaderWithNewCorrelationId(ServerWebExchange exchange, String correlationId) {
        return exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header(FilterUtility.CORRELATION_ID, correlationId)
                        .build())
                .build();
    }
}
