package com.smartbank.customerservice.saga;

import com.smartbank.common.commands.*;
import com.smartbank.common.events.*;
import com.smartbank.customerservice.constants.CustomerConstants;
import com.smartbank.customerservice.dtos.ResponseDTO;
import com.smartbank.customerservice.queries.FindCustomerQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Saga
@Slf4j
public class UpdateMobileNumberSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryUpdateEmitter queryUpdateEmitter;

    @StartSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void handle(CustomerMobileNumberUpdatedEvent customerMobileNumberUpdatedEvent) {
        log.info("Saga event 1 [start] : Received CustomerMobileNumberUpdatedEvent for customerId {}", customerMobileNumberUpdatedEvent.getCustomerId());
        UpdateAccountMobileNumberCommand updateAccountMobileNumberCommand = UpdateAccountMobileNumberCommand.builder()
                .customerId(customerMobileNumberUpdatedEvent.getCustomerId())
                .accountNumber(customerMobileNumberUpdatedEvent.getAccountNumber())
                .loanNumber(customerMobileNumberUpdatedEvent.getLoanNumber())
                .cardNumber(customerMobileNumberUpdatedEvent.getCardNumber())
                .mobileNumber(customerMobileNumberUpdatedEvent.getMobileNumber())
                .newMobileNumber(customerMobileNumberUpdatedEvent.getNewMobileNumber())
                .build();
        commandGateway.send(updateAccountMobileNumberCommand, new CommandCallback<>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateAccountMobileNumberCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()) {
                    RollbackCustomerMobileNumberCommand rollbackCustomerMobileNumberCommand = RollbackCustomerMobileNumberCommand.builder()
                            .customerId(customerMobileNumberUpdatedEvent.getCustomerId())
                            .mobileNumber(customerMobileNumberUpdatedEvent.getMobileNumber())
                            .newMobileNumber(customerMobileNumberUpdatedEvent.getNewMobileNumber())
                            .errorMessage(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.send(rollbackCustomerMobileNumberCommand);
                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void handle(AccountMobileNumberUpdatedEvent accountMobileNumberUpdatedEvent) {
        log.info("Saga event 2 : Received AccountMobileNumberUpdatedEvent for Account Number {}", accountMobileNumberUpdatedEvent.getAccountNumber());
        UpdateCardMobileNumberCommand updateCardMobileNumberCommand = UpdateCardMobileNumberCommand.builder()
                .customerId(accountMobileNumberUpdatedEvent.getCustomerId())
                .accountNumber(accountMobileNumberUpdatedEvent.getAccountNumber())
                .loanNumber(accountMobileNumberUpdatedEvent.getLoanNumber())
                .cardNumber(accountMobileNumberUpdatedEvent.getCardNumber())
                .mobileNumber(accountMobileNumberUpdatedEvent.getMobileNumber())
                .newMobileNumber(accountMobileNumberUpdatedEvent.getNewMobileNumber())
                .build();
        commandGateway.send(updateCardMobileNumberCommand, new CommandCallback<>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateCardMobileNumberCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()) {
                    RollbackAccountMobileNumberCommand rollbackAccountMobileNumberCommand = RollbackAccountMobileNumberCommand.builder()
                            .customerId(accountMobileNumberUpdatedEvent.getCustomerId())
                            .accountNumber(accountMobileNumberUpdatedEvent.getAccountNumber())
                            .mobileNumber(accountMobileNumberUpdatedEvent.getMobileNumber())
                            .newMobileNumber(accountMobileNumberUpdatedEvent.getNewMobileNumber())
                            .errorMessage(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.send(rollbackAccountMobileNumberCommand);
                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void handle(CardMobileNumberUpdatedEvent cardMobileNumberUpdatedEvent) {
        log.info("Saga event 3 : Received CardMobileNumberUpdatedEvent for Card Number {}", cardMobileNumberUpdatedEvent.getCardNumber());
        UpdateLoanMobileNumberCommand updateLoanMobileNumberCommand = UpdateLoanMobileNumberCommand.builder()
                .customerId(cardMobileNumberUpdatedEvent.getCustomerId())
                .accountNumber(cardMobileNumberUpdatedEvent.getAccountNumber())
                .loanNumber(cardMobileNumberUpdatedEvent.getLoanNumber())
                .cardNumber(cardMobileNumberUpdatedEvent.getCardNumber())
                .mobileNumber(cardMobileNumberUpdatedEvent.getMobileNumber())
                .newMobileNumber(cardMobileNumberUpdatedEvent.getNewMobileNumber())
                .build();
        commandGateway.send(updateLoanMobileNumberCommand, new CommandCallback<>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateLoanMobileNumberCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()) {
                    RollbackCardMobileNumberCommand rollbackCardMobileNumberCommand = RollbackCardMobileNumberCommand.builder()
                            .customerId(cardMobileNumberUpdatedEvent.getCustomerId())
                            .accountNumber(cardMobileNumberUpdatedEvent.getAccountNumber())
                            .cardNumber(cardMobileNumberUpdatedEvent.getCardNumber())
                            .mobileNumber(cardMobileNumberUpdatedEvent.getMobileNumber())
                            .newMobileNumber(cardMobileNumberUpdatedEvent.getNewMobileNumber())
                            .errorMessage(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.send(rollbackCardMobileNumberCommand);
                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void handle(LoanMobileNumberUpdatedEvent loanMobileNumberUpdatedEvent) {
        log.info("Saga event 4 : Received LoanMobileNumberUpdatedEvent for Loan Number {}", loanMobileNumberUpdatedEvent.getLoanNumber());
        UpdateProfileMobileNumberCommand updateProfileMobileNumberCommand = UpdateProfileMobileNumberCommand.builder()
                .customerId(loanMobileNumberUpdatedEvent.getCustomerId())
                .mobileNumber(loanMobileNumberUpdatedEvent.getMobileNumber())
                .newMobileNumber(loanMobileNumberUpdatedEvent.getNewMobileNumber())
                .accountNumber(loanMobileNumberUpdatedEvent.getAccountNumber())
                .cardNumber(loanMobileNumberUpdatedEvent.getCardNumber())
                .loanNumber(loanMobileNumberUpdatedEvent.getLoanNumber())
                .build();
        commandGateway.send(updateProfileMobileNumberCommand, new CommandCallback<>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateProfileMobileNumberCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()) {
                    log.info("Error At profile mobile number update {}", commandResultMessage.exceptionResult().getMessage());
                    RollbackLoanMobileNumberCommand rollbackLoanMobileNumberCommand = RollbackLoanMobileNumberCommand.builder()
                            .customerId(loanMobileNumberUpdatedEvent.getCustomerId())
                            .accountNumber(loanMobileNumberUpdatedEvent.getAccountNumber())
                            .loanNumber(loanMobileNumberUpdatedEvent.getLoanNumber())
                            .cardNumber(loanMobileNumberUpdatedEvent.getCardNumber())
                            .mobileNumber(loanMobileNumberUpdatedEvent.getMobileNumber())
                            .newMobileNumber(loanMobileNumberUpdatedEvent.getNewMobileNumber())
                            .errorMessage(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.send(rollbackLoanMobileNumberCommand);
                }
            }
        });
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void handle(ProfileMobileNumberUpdatedEvent profileMobileNumberUpdatedEvent) {
        log.info("Saga event 5 [end] : Received ProfileMobileNumberUpdatedEvent for Mobile Number {}", profileMobileNumberUpdatedEvent.getMobileNumber());
        queryUpdateEmitter.emit(FindCustomerQuery.class, query -> true, new ResponseDTO(CustomerConstants.STATUS_200, CustomerConstants.MOBILE_UPDATE_SUCCESS_MESSAGE));
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void handle(LoanMobileNumberRollbackedEvent loanMobileNumberRollbackedEvent) {
        log.info("Saga compensation event : Received LoanMobileNumberRollbackedEvent for Loan Number {}", loanMobileNumberRollbackedEvent.getLoanNumber());
        RollbackCardMobileNumberCommand rollbackCardMobileNumberCommand = RollbackCardMobileNumberCommand.builder()
                .customerId(loanMobileNumberRollbackedEvent.getCustomerId())
                .accountNumber(loanMobileNumberRollbackedEvent.getAccountNumber())
                .cardNumber(loanMobileNumberRollbackedEvent.getCardNumber())
                .mobileNumber(loanMobileNumberRollbackedEvent.getMobileNumber())
                .newMobileNumber(loanMobileNumberRollbackedEvent.getNewMobileNumber())
                .errorMessage(loanMobileNumberRollbackedEvent.getErrorMessage())
                .build();
        commandGateway.send(rollbackCardMobileNumberCommand);
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void handle(CardMobileNumberRollbackedEvent cardMobileNumberRollbackedEvent) {
        log.info("Saga compensation event : Received CardMobileNumberRollbackedEvent for Card Number {}", cardMobileNumberRollbackedEvent.getCardNumber());
        RollbackAccountMobileNumberCommand rollbackAccountMobileNumberCommand = RollbackAccountMobileNumberCommand.builder()
                .customerId(cardMobileNumberRollbackedEvent.getCustomerId())
                .accountNumber(cardMobileNumberRollbackedEvent.getAccountNumber())
                .mobileNumber(cardMobileNumberRollbackedEvent.getMobileNumber())
                .newMobileNumber(cardMobileNumberRollbackedEvent.getNewMobileNumber())
                .errorMessage(cardMobileNumberRollbackedEvent.getErrorMessage())
                .build();
        commandGateway.send(rollbackAccountMobileNumberCommand);
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void handle(AccountMobileNumberRollbackedEvent accountMobileNumberRollbackedEvent) {
        log.info("Saga compensation event : Received AccountMobileNumberRollbackedEvent for Account Number {}", accountMobileNumberRollbackedEvent.getAccountNumber());
        RollbackCustomerMobileNumberCommand rollbackCustomerMobileNumberCommand = RollbackCustomerMobileNumberCommand.builder()
                .customerId(accountMobileNumberRollbackedEvent.getCustomerId())
                .mobileNumber(accountMobileNumberRollbackedEvent.getMobileNumber())
                .newMobileNumber(accountMobileNumberRollbackedEvent.getNewMobileNumber())
                .errorMessage(accountMobileNumberRollbackedEvent.getErrorMessage())
                .build();
        commandGateway.send(rollbackCustomerMobileNumberCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void handle(CustomerMobileNumberRollbackedEvent customerMobileNumberRollbackedEvent) {
        log.info("Saga compensation event [END] : Received CustomerMobileNumberRollbackedEvent for Customer Id {}", customerMobileNumberRollbackedEvent.getCustomerId());
        queryUpdateEmitter.emit(FindCustomerQuery.class, query -> true, new ResponseDTO(CustomerConstants.STATUS_500, CustomerConstants.MOBILE_UPDATE_FAILURE_MESSAGE));
    }
}
