package com.smartbank.profileservice.queries.handler;

import com.smartbank.profileservice.dtos.ProfileDTO;
import com.smartbank.profileservice.queries.FindProfileQuery;
import com.smartbank.profileservice.services.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileQueryHandler {
    private final IProfileService customerService;
    @QueryHandler
    public ProfileDTO findProfile(FindProfileQuery findProfileQuery) {
        return customerService.fetchProfile(findProfileQuery.getMobileNumber());
    }
}
