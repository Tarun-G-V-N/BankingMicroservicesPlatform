package com.smartbank.loanservice.services.Impl;

import com.smartbank.common.events.LoanDataChangedEvent;
import com.smartbank.loanservice.command.events.LoanUpdatedEvent;
import com.smartbank.loanservice.constants.LoanConstants;
import com.smartbank.loanservice.dtos.LoansDTO;
import com.smartbank.loanservice.dtos.LoansMessageDTO;
import com.smartbank.loanservice.entities.Loan;
import com.smartbank.loanservice.exceptions.LoanAlreadyExistsException;
import com.smartbank.loanservice.exceptions.ResourceNotFoundException;
import com.smartbank.loanservice.mappers.LoanMapper;
import com.smartbank.loanservice.repositories.LoanRepository;
import com.smartbank.loanservice.services.ILoanService;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {
    public static Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
    private LoanRepository loanRepository;
    private EventGateway eventGateway;
    private StreamBridge streamBridge;
    @Override
    public void createLoan(Loan loan) {
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumberAndIsActive(loan.getMobileNumber(), LoanConstants.ACTIVE_SW);
        if (optionalLoan.isPresent()) throw new LoanAlreadyExistsException("Loan already exists for the provided mobile number: "+loan.getMobileNumber());
        Loan savedLoan = loanRepository.save(loan);
        sendCommunication(savedLoan);
    }

    @Override
    public LoansDTO fetchLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumberAndIsActive(mobileNumber, LoanConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        return LoanMapper.mapToLoansDto(loan, new LoansDTO());
    }

    @Override
    public boolean updateLoan(LoanUpdatedEvent loanUpdatedEvent) {
        Loan loan = loanRepository.findByMobileNumberAndIsActive(loanUpdatedEvent.getMobileNumber(), LoanConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", loanUpdatedEvent.getMobileNumber()));
        loanRepository.save(LoanMapper.mapEventToLoan(loanUpdatedEvent, loan));
        return true;
    }

    @Override
    public boolean deleteLoan(Long loanNumber) {
        Loan loan = loanRepository.findByLoanNumberAndIsActive(loanNumber, LoanConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber.toString()));
        loan.setActive(LoanConstants.IN_ACTIVE_SW);
        loanRepository.save(loan);
        LoanDataChangedEvent loanDataChangedEvent = new LoanDataChangedEvent();
        loanDataChangedEvent.setMobileNumber(loan.getMobileNumber());
        loanDataChangedEvent.setLoanNumber(0L);
        eventGateway.publish(loanDataChangedEvent);
        return true;
    }

    @Override
    public boolean updateMobileNumber(String mobileNumber, String newMobileNumber) {
        Loan loan = loanRepository.findByMobileNumberAndIsActive(mobileNumber, LoanConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        loan.setMobileNumber(newMobileNumber);
        loanRepository.save(loan);
        return true;
    }

    private void sendCommunication(Loan loan) {
        var loansMessageDTO = new LoansMessageDTO(loan.getMobileNumber(), loan.getLoanNumber(), loan.getLoanType());
        logger.debug("Sending Communication request for the details: {}", loansMessageDTO);
        var result = streamBridge.send("sendCommunication-out-0", loansMessageDTO);
        logger.debug("is communication request successfully triggered ? : {}", result);
    }

    @Override
    public boolean updateCommunicationStatus(Long loanNumber) {
        boolean isUpdated = false;
        if(loanNumber != null) {
            Loan loan = loanRepository.findByLoanNumberAndIsActive(loanNumber, LoanConstants.ACTIVE_SW)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", loanNumber.toString()));
            loan.setCommunicationSent(true);
            loanRepository.save(loan);
            isUpdated = true;
        }
        return isUpdated;
    }
}
