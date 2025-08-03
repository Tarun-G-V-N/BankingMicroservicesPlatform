package com.smartbank.loanservice.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FindLoanQuery {
    private final String mobileNumber;

    @JsonCreator
    public FindLoanQuery(@JsonProperty("mobileNumber") String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
