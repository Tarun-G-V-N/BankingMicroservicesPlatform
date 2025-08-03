package com.smartbank.profileservice.services;

import com.smartbank.common.events.*;
import com.smartbank.profileservice.dtos.ProfileDTO;

public interface IProfileService {
    ProfileDTO fetchProfile(String mobileNumber);
    void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent);
    void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent);
    void handleCardDataChangedEvent(CardDataChangedEvent cardDataChangedEvent);
    void handleLoanDataChangedEvent(LoanDataChangedEvent loanDataChangedEvent);
    void handleProfileMobileNumberChangedEvent(ProfileMobileNumberUpdatedEvent profileMobileNumberUpdatedEvent);
}
