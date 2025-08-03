package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountMobileNumberRollbackedEvent {
    private String customerId;
    private Long accountNumber;
    private String mobileNumber;
    private String newMobileNumber;
    private String errorMessage;
}
