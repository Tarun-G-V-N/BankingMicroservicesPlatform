package com.smartbank.accountservice.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteAccountCommand {
    @TargetAggregateIdentifier
    private final Long accountNumber;
    private final boolean isActive;

    @JsonCreator
    public DeleteAccountCommand(@JsonProperty("accountNumber") Long accountNumber, @JsonProperty("active") boolean isActive) {
        this.accountNumber = accountNumber;
        this.isActive = isActive;
    }
}
