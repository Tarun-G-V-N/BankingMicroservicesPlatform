package com.smartbank.accountservice.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateAccountCommand {
    @TargetAggregateIdentifier
    private final Long accountNumber;
    private final String mobileNumber;
    private final String accountType;
    private final String branchAddress;
    private final boolean isActive;

    @JsonCreator
    public CreateAccountCommand(@JsonProperty("accountNumber") Long accountNumber,
                                @JsonProperty("mobileNumber") String mobileNumber, @JsonProperty("accountType") String accountType,
                                @JsonProperty("branchAddress") String branchAddress, @JsonProperty("active") boolean isActive) {
        this.accountNumber = accountNumber;
        this.mobileNumber = mobileNumber;
        this.accountType = accountType;
        this.branchAddress = branchAddress;
        this.isActive = isActive;
    }
}
