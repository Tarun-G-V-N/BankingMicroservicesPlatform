package com.smartbank.customerservice.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateCustomerCommand {
    @TargetAggregateIdentifier
    private final String customerId;
    private final String name;
    private final String email;
    private final String mobileNumber;
    private final boolean isActive;

    @JsonCreator
    public CreateCustomerCommand(@JsonProperty("customerId") String customerId,
                                 @JsonProperty("name") String name, @JsonProperty("email") String email,
                                 @JsonProperty("mobileNumber") String mobileNumber, @JsonProperty("active") boolean isActive) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.isActive = isActive;
    }
}
