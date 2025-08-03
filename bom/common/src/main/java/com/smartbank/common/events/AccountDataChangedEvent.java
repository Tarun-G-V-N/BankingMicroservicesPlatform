package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDataChangedEvent {
    private String mobileNumber;
    private Long accountNumber;
}
