package com.smartbank.common.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateLoanMobileNumberCommand {
    @TargetAggregateIdentifier
    private final Long loanNumber;
    private final String customerId;
    private final Long accountNumber;
    private final Long cardNumber;
    private final String mobileNumber;
    private final String newMobileNumber;

    @JsonCreator
    public UpdateLoanMobileNumberCommand(@JsonProperty("loanNumber") Long loanNumber, @JsonProperty("customerId") String customerId,
                                         @JsonProperty("accountNumber") Long accountNumber, @JsonProperty("cardNumber") Long cardNumber,
                                         @JsonProperty("mobileNumber") String mobileNumber, @JsonProperty("newMobileNumber") String newMobileNumber) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.loanNumber = loanNumber;
        this.cardNumber = cardNumber;
        this.mobileNumber = mobileNumber;
        this.newMobileNumber = newMobileNumber;
    }
}
