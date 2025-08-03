package com.smartbank.cardservice.command.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDeletedEvent {
    private Long cardNumber;
    private boolean isActive;
}
