package com.smartbank.accountservice.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FindAccountQuery {
    private final String mobileNumber;

    @JsonCreator
    public FindAccountQuery(@JsonProperty("mobileNumber") String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
