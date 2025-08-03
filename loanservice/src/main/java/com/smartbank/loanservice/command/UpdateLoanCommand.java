package com.smartbank.loanservice.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateLoanCommand {
    @TargetAggregateIdentifier
    private final Long loanNumber;
    private final String mobileNumber;
    private final String loanType;
    private final int totalLoan;
    private final int amountPaid;
    private final int outstandingAmount;
    private final boolean isActive;

    @JsonCreator
    public UpdateLoanCommand(@JsonProperty("loanNumber") Long loanNumber, @JsonProperty("mobileNumber") String mobileNumber,
                             @JsonProperty("loanType") String loanType, @JsonProperty("totalLoan") int totalLoan,
                             @JsonProperty("amountPaid") int amountPaid, @JsonProperty("outstandingAmount") int outstandingAmount,
                             @JsonProperty("active") boolean isActive) {
        this.loanNumber = loanNumber;
        this.mobileNumber = mobileNumber;
        this.loanType = loanType;
        this.totalLoan = totalLoan;
        this.amountPaid = amountPaid;
        this.outstandingAmount = outstandingAmount;
        this.isActive = isActive;
    }
}
