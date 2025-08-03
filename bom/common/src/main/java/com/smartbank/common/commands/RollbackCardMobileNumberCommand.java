package com.smartbank.common.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class RollbackCardMobileNumberCommand {
    @TargetAggregateIdentifier
    private final Long cardNumber;
    private final String customerId;
    private final Long accountNumber;
    private final String mobileNumber;
    private final String newMobileNumber;
    private final String errorMessage;

    @JsonCreator
    public RollbackCardMobileNumberCommand(@JsonProperty("cardNumber") Long cardNumber, @JsonProperty("customerId") String customerId,
                                           @JsonProperty("accountNumber") Long accountNumber, @JsonProperty("mobileNumber") String mobileNumber,
                                           @JsonProperty("newMobileNumber") String newMobileNumber, @JsonProperty("errorMessage") String errorMessage) {
        this.cardNumber = cardNumber;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.mobileNumber = mobileNumber;
        this.newMobileNumber = newMobileNumber;
        this.errorMessage = errorMessage;
    }
}
