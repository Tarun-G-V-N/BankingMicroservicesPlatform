package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerMobileNumberRollbackedEvent {
    private String customerId;
    private String mobileNumber;
    private String newMobileNumber;
    public String errorMessage;
}
