package com.smartbank.profileservice.queries.Projection;

import com.smartbank.common.events.*;
import com.smartbank.profileservice.services.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileProjection {
    private final IProfileService profileService;

    @EventHandler
    public void on(CustomerDataChangedEvent customerDataChangedEvent) {
        profileService.handleCustomerDataChangedEvent(customerDataChangedEvent);
    }

    @EventHandler
    public void on(AccountDataChangedEvent accountDataChangedEvent) {
        profileService.handleAccountDataChangedEvent(accountDataChangedEvent);
    }

    @EventHandler
    public void on(CardDataChangedEvent cardDataChangedEvent) {
        profileService.handleCardDataChangedEvent(cardDataChangedEvent);
    }

    @EventHandler
    public void on(LoanDataChangedEvent loanDataChangedEvent) {
        profileService.handleLoanDataChangedEvent(loanDataChangedEvent);
    }

    @EventHandler
    public void on(ProfileMobileNumberUpdatedEvent profileMobileNumberUpdatedEvent) {
        profileService.handleProfileMobileNumberChangedEvent(profileMobileNumberUpdatedEvent);
    }
}
