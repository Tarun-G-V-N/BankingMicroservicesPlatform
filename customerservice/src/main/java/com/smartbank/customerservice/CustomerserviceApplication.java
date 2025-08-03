package com.smartbank.customerservice;

import com.smartbank.common.config.AxonConfig;
import com.smartbank.customerservice.commands.interceptor.CustomerCommandInterceptor;
import com.smartbank.customerservice.dtos.ContactInfoDTO;
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
				title = "Customer Microservice",
				description = "Customer Microservice REST API Documentation",
				version = "1.0",
				contact = @Contact(
						name = "Tarun",
						email = "tarungandikota9012@gmail.com"
				)
		)
)
public class CustomerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerserviceApplication.class, args);
	}

	@Autowired
	public void registerCustomerCommandInterceptor(ApplicationContext context, CommandGateway commandGateway) {
		commandGateway.registerDispatchInterceptor(context.getBean(CustomerCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("customer-group",
				conf -> PropagatingErrorHandler.instance());
	}

//	@Bean(name="customerSnapshotTrigger")
//	public SnapshotTriggerDefinition customerSnapshotTrigger(Snapshotter snapshotter) {
//		return new EventCountSnapshotTriggerDefinition(snapshotter,3);
//	}
}
