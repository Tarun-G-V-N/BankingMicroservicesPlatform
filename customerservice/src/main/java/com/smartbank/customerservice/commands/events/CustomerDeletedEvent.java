package com.smartbank.customerservice.commands.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDeletedEvent {
    private String customerId;
    private boolean isActive;
}
