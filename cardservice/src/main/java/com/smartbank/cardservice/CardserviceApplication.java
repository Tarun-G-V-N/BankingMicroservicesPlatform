package com.smartbank.cardservice;

import com.smartbank.cardservice.command.interceptor.CardCommandInterceptor;
import com.smartbank.cardservice.dtos.ContactInfoDTO;
import com.smartbank.common.config.AxonConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@Import(AxonConfig.class)
@EnableConfigurationProperties(value = {ContactInfoDTO.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Cards Microservice",
				description = "Card Microservice REST API Documentation",
				version = "1.0",
				contact = @Contact(
						name = "Tarun",
						email = "tarungandikota9012@gmail.com"
				)
		)
)
public class CardserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardserviceApplication.class, args);
	}

	@Autowired
	public void registerCustomerCommandInterceptor(ApplicationContext context, CommandGateway commandGateway) {
		commandGateway.registerDispatchInterceptor(context.getBean(CardCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("card-group",
				conf -> PropagatingErrorHandler.instance());
	}

//	@Bean(name="cardSnapshotTrigger")
//	public SnapshotTriggerDefinition cardSnapshotTrigger(Snapshotter snapshotter) {
//		return new EventCountSnapshotTriggerDefinition(snapshotter,3);
//	}
}
