package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileMobileNumberUpdatedEvent {
    private String mobileNumber;
    private Long cardNumber;
    private String customerId;
    private Long accountNumber;
    private Long loanNumber;
    private String newMobileNumber;
}
