package com.smartbank.common.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class RollbackAccountMobileNumberCommand {
    @TargetAggregateIdentifier
    private final Long accountNumber;
    private final String customerId;
    private final String mobileNumber;
    private final String newMobileNumber;
    private final String errorMessage;

    @JsonCreator
    public RollbackAccountMobileNumberCommand(@JsonProperty("accountNumber") Long accountNumber, @JsonProperty("customerId") String customerId,
                                              @JsonProperty("mobileNumber") String mobileNumber, @JsonProperty("newMobileNumber") String newMobileNumber,
                                              @JsonProperty("errorMessage") String errorMessage) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.mobileNumber = mobileNumber;
        this.newMobileNumber = newMobileNumber;
        this.errorMessage = errorMessage;
    }
}
