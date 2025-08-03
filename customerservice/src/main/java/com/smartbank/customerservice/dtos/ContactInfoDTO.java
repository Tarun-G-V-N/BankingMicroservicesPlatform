package com.smartbank.customerservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(value = "customers")
@Getter
@Setter
public class ContactInfoDTO {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> supportContacts;
}
