package com.smartbank.loanservice.command.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanDeletedEvent {
    private Long loanNumber;
    private boolean isActive;
}
