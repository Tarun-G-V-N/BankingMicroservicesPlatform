package com.smartbank.profileservice;

import com.smartbank.common.config.AxonConfig;
import com.smartbank.profileservice.dtos.ContactInfoDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@Import(AxonConfig.class)
@EnableConfigurationProperties(value = {ContactInfoDTO.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Profile Microservice",
				description = "Profile Microservice REST API Documentation",
				version = "1.0",
				contact = @Contact(
						name = "Tarun",
						email = "tarungandikota9012@gmail.com"
				)
		)
)
public class ProfileserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileserviceApplication.class, args);
	}
}
