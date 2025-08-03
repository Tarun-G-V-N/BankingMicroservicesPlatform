package com.smartbank.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.eventsourcing.EventSourcedAggregate;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {
    @Bean
    @Primary
    public Serializer serializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(EventSourcedAggregate.class, IgnoreSnapshotTriggerMixIn.class);

        return JacksonSerializer.builder()
                .objectMapper(mapper)
                .build();
    }
}
