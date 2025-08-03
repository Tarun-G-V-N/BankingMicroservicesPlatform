package com.smartbank.accountservice.command.interceptor;

import com.smartbank.accountservice.command.CreateAccountCommand;
import com.smartbank.accountservice.command.DeleteAccountCommand;
import com.smartbank.accountservice.command.UpdateAccountCommand;
import com.smartbank.accountservice.constants.AccountConstants;
import com.smartbank.accountservice.entities.Account;
import com.smartbank.accountservice.exceptions.AccountAlreadyExistsException;
import com.smartbank.accountservice.exceptions.ResourceNotFoundException;
import com.smartbank.accountservice.repositories.AccountRepository;
import com.smartbank.common.commands.UpdateAccountMobileNumberCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class AccountCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final AccountRepository accountRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if(CreateAccountCommand.class.equals(command.getPayloadType())) {
                CreateAccountCommand createAccountCommand = (CreateAccountCommand) command.getPayload();
                Optional<Account> optionalAccount = accountRepository.findByMobileNumberAndIsActive(createAccountCommand.getMobileNumber(), AccountConstants.ACTIVE_SW);
                if(optionalAccount.isPresent()) throw new AccountAlreadyExistsException("Account with mobile number " + createAccountCommand.getMobileNumber() + " already exists");
            } else if (UpdateAccountCommand.class.equals(command.getPayloadType())) {
                UpdateAccountCommand updateAccountCommand = (UpdateAccountCommand) command.getPayload();
                Account account = accountRepository.findByMobileNumberAndIsActive(updateAccountCommand.getMobileNumber(), AccountConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", updateAccountCommand.getMobileNumber()));
            } else if (DeleteAccountCommand.class.equals(command.getPayloadType())) {
                DeleteAccountCommand deleteAccountCommand = (DeleteAccountCommand) command.getPayload();
                Account account = accountRepository.findByAccountNumberAndIsActive(deleteAccountCommand.getAccountNumber(), AccountConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", deleteAccountCommand.getAccountNumber().toString()));

            } else if (UpdateAccountMobileNumberCommand.class.equals(command.getPayloadType())) {
                UpdateAccountMobileNumberCommand updateAccountMobileNumberCommand = (UpdateAccountMobileNumberCommand) command.getPayload();
                Account account = accountRepository.findByMobileNumberAndIsActive(updateAccountMobileNumberCommand.getMobileNumber(), AccountConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", updateAccountMobileNumberCommand.getMobileNumber()));
            }
            return command;
        };
    }
}
