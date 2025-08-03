package com.smartbank.accountservice.command.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDeletedEvent {
    private Long accountNumber;
    private boolean isActive;
}
