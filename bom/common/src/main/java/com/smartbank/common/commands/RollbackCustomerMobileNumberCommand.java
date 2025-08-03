package com.smartbank.common.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class RollbackCustomerMobileNumberCommand {
    @TargetAggregateIdentifier
    private final String customerId;
    private final String mobileNumber;
    private final String newMobileNumber;
    public final String errorMessage;

    @JsonCreator
    public RollbackCustomerMobileNumberCommand(@JsonProperty("customerId") String customerId, @JsonProperty("mobileNumber") String mobileNumber,
                                               @JsonProperty("newMobileNumber") String newMobileNumber, @JsonProperty("errorMessage") String errorMessage) {
        this.customerId = customerId;
        this.mobileNumber = mobileNumber;
        this.newMobileNumber = newMobileNumber;
        this.errorMessage = errorMessage;
    }
}
