package com.smartbank.loanservice.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteLoanCommand {
    @TargetAggregateIdentifier
    private final Long loanNumber;
    private final boolean isActive;

    @JsonCreator
    public DeleteLoanCommand(@JsonProperty("loanNumber") Long loanNumber, @JsonProperty("active") boolean isActive) {
        this.loanNumber = loanNumber;
        this.isActive = isActive;
    }
}
