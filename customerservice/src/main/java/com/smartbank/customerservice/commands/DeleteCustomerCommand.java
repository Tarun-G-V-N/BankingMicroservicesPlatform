package com.smartbank.customerservice.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteCustomerCommand {
    @TargetAggregateIdentifier
    private final String customerId;
    private final boolean isActive;

    @JsonCreator
    public DeleteCustomerCommand(@JsonProperty("customerId") String customerId,  @JsonProperty("active") boolean isActive) {
        this.customerId = customerId;
        this.isActive = isActive;
    }
}
