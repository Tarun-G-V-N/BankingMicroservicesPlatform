package com.smartbank.customerservice.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FindCustomerQuery {
    private final String mobileNumber;

    @JsonCreator
    public FindCustomerQuery(@JsonProperty("mobileNumber") String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
