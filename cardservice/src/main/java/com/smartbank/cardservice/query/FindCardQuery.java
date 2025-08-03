package com.smartbank.cardservice.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FindCardQuery {
    private final String mobileNumber;

    @JsonCreator
    public FindCardQuery(@JsonProperty("mobileNumber") String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
