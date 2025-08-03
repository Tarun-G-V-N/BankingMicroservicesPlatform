package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanDataChangedEvent {
    private String mobileNumber;
    private Long loanNumber;
}
