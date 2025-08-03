package com.smartbank.profileservice.mappers;


import com.smartbank.profileservice.dtos.ProfileDTO;
import com.smartbank.profileservice.entities.Profile;

public class ProfileMapper {
    public static ProfileDTO mapToProfileDto(Profile profile, ProfileDTO profileDto) {
        profileDto.setName(profile.getName());
        profileDto.setMobileNumber(profile.getMobileNumber());
        profileDto.setAccountNumber(profile.getAccountNumber());
        profileDto.setCardNumber(profile.getCardNumber());
        profileDto.setLoanNumber(profile.getLoanNumber());
        return profileDto;
    }
}
