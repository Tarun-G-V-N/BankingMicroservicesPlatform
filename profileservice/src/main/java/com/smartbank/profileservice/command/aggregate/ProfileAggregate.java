package com.smartbank.profileservice.command.aggregate;

import com.smartbank.common.commands.UpdateProfileMobileNumberCommand;
import com.smartbank.common.events.ProfileMobileNumberUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Slf4j
@Aggregate
public class ProfileAggregate {
    @AggregateIdentifier
    public String mobileNumber;
    public String newMobileNumber;
    public Long accountNumber;
    public Long cardNumber;
    public Long loanNumber;
    public String customerId;

    protected ProfileAggregate() {}

    @CommandHandler
    public ProfileAggregate(UpdateProfileMobileNumberCommand updateProfileMobileNumberCommand) {
        ProfileMobileNumberUpdatedEvent profileMobileNumberUpdatedEvent = new ProfileMobileNumberUpdatedEvent();
        BeanUtils.copyProperties(updateProfileMobileNumberCommand, profileMobileNumberUpdatedEvent);
        log.info("Profile Mobile Number {}", profileMobileNumberUpdatedEvent.getMobileNumber());
        log.info("Profile new Mobile Number {}", profileMobileNumberUpdatedEvent.getNewMobileNumber());
        AggregateLifecycle.apply(profileMobileNumberUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ProfileMobileNumberUpdatedEvent profileMobileNumberUpdatedEvent) {
        this.mobileNumber = profileMobileNumberUpdatedEvent.getMobileNumber();
        this.newMobileNumber = profileMobileNumberUpdatedEvent.getNewMobileNumber();
        this.accountNumber = profileMobileNumberUpdatedEvent.getAccountNumber();
        this.cardNumber = profileMobileNumberUpdatedEvent.getCardNumber();
        this.loanNumber = profileMobileNumberUpdatedEvent.getLoanNumber();
        this.customerId = profileMobileNumberUpdatedEvent.getCustomerId();
    }
}
