package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardMobileNumberRollbackedEvent {
    private String customerId;
    private Long accountNumber;
    private Long cardNumber;
    private String mobileNumber;
    private String newMobileNumber;
    private String errorMessage;
}
