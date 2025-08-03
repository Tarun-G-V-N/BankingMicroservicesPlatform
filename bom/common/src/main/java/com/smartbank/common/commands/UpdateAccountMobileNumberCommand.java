package com.smartbank.common.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateAccountMobileNumberCommand {

    @TargetAggregateIdentifier
    private final Long accountNumber;
    private final String customerId;
    private final Long loanNumber;
    private final Long cardNumber;
    private final String mobileNumber;
    private final String newMobileNumber;

    @JsonCreator
    public UpdateAccountMobileNumberCommand(@JsonProperty("accountNumber") Long accountNumber, @JsonProperty("customerId") String customerId,
                                             @JsonProperty("loanNumber") Long loanNumber, @JsonProperty("cardNumber") Long cardNumber,
                                             @JsonProperty("mobileNumber") String mobileNumber, @JsonProperty("newMobileNumber") String newMobileNumber) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.loanNumber = loanNumber;
        this.cardNumber = cardNumber;
        this.mobileNumber = mobileNumber;
        this.newMobileNumber = newMobileNumber;
    }
}
