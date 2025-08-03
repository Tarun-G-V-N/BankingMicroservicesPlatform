package com.smartbank.loanservice.command.interceptor;

import com.smartbank.common.commands.UpdateLoanMobileNumberCommand;
import com.smartbank.loanservice.command.CreateLoanCommand;
import com.smartbank.loanservice.command.DeleteLoanCommand;
import com.smartbank.loanservice.command.UpdateLoanCommand;
import com.smartbank.loanservice.constants.LoanConstants;
import com.smartbank.loanservice.entities.Loan;
import com.smartbank.loanservice.exceptions.LoanAlreadyExistsException;
import com.smartbank.loanservice.exceptions.ResourceNotFoundException;
import com.smartbank.loanservice.repositories.LoanRepository;
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
public class LoanCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final LoanRepository loanRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (CreateLoanCommand.class.equals(command.getPayloadType())) {
                CreateLoanCommand createLoanCommand = (CreateLoanCommand) command.getPayload();
                Optional<Loan> optionalLoan = loanRepository.findByMobileNumberAndIsActive(createLoanCommand.getMobileNumber(), LoanConstants.ACTIVE_SW);
                if (optionalLoan.isPresent())
                    throw new LoanAlreadyExistsException("Loan already exists for the provided mobile number: " + createLoanCommand.getMobileNumber());
            } else if (UpdateLoanCommand.class.equals(command.getPayloadType())) {
                UpdateLoanCommand updateLoanCommand = (UpdateLoanCommand) command.getPayload();
                Loan loan = loanRepository.findByMobileNumberAndIsActive(updateLoanCommand.getMobileNumber(), LoanConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", updateLoanCommand.getMobileNumber()));
            } else if (DeleteLoanCommand.class.equals(command.getPayloadType())) {
                DeleteLoanCommand deleteLoanCommand = (DeleteLoanCommand) command.getPayload();
                Loan loan = loanRepository.findByLoanNumberAndIsActive(deleteLoanCommand.getLoanNumber(), LoanConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", deleteLoanCommand.getLoanNumber().toString()));
            } else if (UpdateLoanMobileNumberCommand.class.equals(command.getPayloadType())) {
                UpdateLoanMobileNumberCommand updateLoanMobileNumberCommand = (UpdateLoanMobileNumberCommand) command.getPayload();
                Loan loan = loanRepository.findByMobileNumberAndIsActive(updateLoanMobileNumberCommand.getMobileNumber(), LoanConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", updateLoanMobileNumberCommand.getMobileNumber()));
            }
            return command;
        };
    }
}
