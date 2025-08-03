package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDataChangedEvent {
    private String mobileNumber;
    private Long cardNumber;
}
