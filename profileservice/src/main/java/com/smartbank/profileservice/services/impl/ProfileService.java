package com.smartbank.profileservice.services.impl;

import com.smartbank.common.events.*;
import com.smartbank.profileservice.dtos.ProfileDTO;

import com.smartbank.profileservice.entities.Profile;
import com.smartbank.profileservice.exceptions.ResourceNotFoundException;
import com.smartbank.profileservice.mappers.ProfileMapper;
import com.smartbank.profileservice.constants.ProfileConstants;
import com.smartbank.profileservice.repositories.ProfileRepository;
import com.smartbank.profileservice.services.IProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileService implements IProfileService {
    private ProfileRepository profileRepository;

    @Override
    public ProfileDTO fetchProfile(String mobileNumber) {
        Profile profile = profileRepository.findByMobileNumberAndIsActive(mobileNumber, ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", mobileNumber));
        return ProfileMapper.mapToProfileDto(profile, new ProfileDTO());
    }

    @Override
    public void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndIsActive(customerDataChangedEvent.getMobileNumber(), ProfileConstants.ACTIVE_SW)
                .orElseGet(Profile::new);
        profile.setName(customerDataChangedEvent.getName());
        profile.setMobileNumber(customerDataChangedEvent.getMobileNumber());
        profile.setActive(customerDataChangedEvent.isActive());
        profileRepository.save(profile);
    }

    @Override
    public void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndIsActive(accountDataChangedEvent.getMobileNumber(), ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", accountDataChangedEvent.getMobileNumber()));
        profile.setAccountNumber(accountDataChangedEvent.getAccountNumber());
        profileRepository.save(profile);
    }

    @Override
    public void handleCardDataChangedEvent(CardDataChangedEvent cardDataChangedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndIsActive(cardDataChangedEvent.getMobileNumber(), ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", cardDataChangedEvent.getMobileNumber()));
        profile.setCardNumber(cardDataChangedEvent.getCardNumber());
        profileRepository.save(profile);
    }

    @Override
    public void handleLoanDataChangedEvent(LoanDataChangedEvent loanDataChangedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndIsActive(loanDataChangedEvent.getMobileNumber(), ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", loanDataChangedEvent.getMobileNumber()));
        profile.setLoanNumber(loanDataChangedEvent.getLoanNumber());
        profileRepository.save(profile);
    }

    @Override
    public void handleProfileMobileNumberChangedEvent(ProfileMobileNumberUpdatedEvent profileMobileNumberUpdatedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndIsActive(profileMobileNumberUpdatedEvent.getMobileNumber(), ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", profileMobileNumberUpdatedEvent.getMobileNumber()));
        profile.setMobileNumber(profileMobileNumberUpdatedEvent.getNewMobileNumber());
        profileRepository.save(profile);
    }
}
