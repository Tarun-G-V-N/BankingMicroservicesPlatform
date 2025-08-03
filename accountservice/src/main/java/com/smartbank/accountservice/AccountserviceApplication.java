package com.smartbank.accountservice;

import com.smartbank.accountservice.command.interceptor.AccountCommandInterceptor;
import com.smartbank.accountservice.dtos.ContactInfoDTO;
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
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.smartbank.accountservice.controllers") })
@EnableJpaRepositories("com.smartbank.accountservice.repository")
@EntityScan("com.smartbank.accountservice.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@Import(AxonConfig.class)
@EnableConfigurationProperties(value = {ContactInfoDTO.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice",
				description = "Account Microservice REST API Documentation",
				version = "1.0",
				contact = @Contact(
						  name = "Tarun",
						  email = "tarungandikota9012@gmail.com"
				)
		)
)
public class AccountserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountserviceApplication.class, args);
	}

	@Autowired
	public void registerCustomerCommandInterceptor(ApplicationContext context, CommandGateway commandGateway) {
		commandGateway.registerDispatchInterceptor(context.getBean(AccountCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("account-group",
				conf -> PropagatingErrorHandler.instance());
	}

//	@Bean(name="accountSnapshotTrigger")
//	public SnapshotTriggerDefinition accountSnapshotTrigger(Snapshotter snapshotter) {
//		return new EventCountSnapshotTriggerDefinition(snapshotter,3);
//	}
}
