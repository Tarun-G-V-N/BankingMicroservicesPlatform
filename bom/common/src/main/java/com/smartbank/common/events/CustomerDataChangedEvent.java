package com.smartbank.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDataChangedEvent {
    private String name;
    private String mobileNumber;
    private boolean active;
}
