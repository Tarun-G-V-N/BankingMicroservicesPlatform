package com.smartbank.profileservice.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FindProfileQuery {
    private final String mobileNumber;

    @JsonCreator
    public FindProfileQuery(@JsonProperty("mobileNumber") String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
