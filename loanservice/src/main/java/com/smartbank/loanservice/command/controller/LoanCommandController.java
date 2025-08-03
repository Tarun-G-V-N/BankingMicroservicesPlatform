package com.smartbank.loanservice.command.controller;

import com.smartbank.loanservice.command.CreateLoanCommand;
import com.smartbank.loanservice.command.DeleteLoanCommand;
import com.smartbank.loanservice.command.UpdateLoanCommand;
import com.smartbank.loanservice.constants.LoanConstants;
import com.smartbank.loanservice.dtos.ErrorResponseDTO;
import com.smartbank.loanservice.dtos.LoansDTO;
import com.smartbank.loanservice.dtos.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Tag(
        name = " CRUD REST APIs for Loan Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Loan Microservice"
)
@RestController
@RequestMapping("/loans")
@AllArgsConstructor
@Validated
public class LoanCommandController {
    private final CommandGateway commandGateway;
    @Operation(summary = "Create Loan REST API", description = "REST API to create new loan inside EazyBank")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoan(@RequestParam("mobileNumber") @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber) {
        long randomLoanNumber = 1000000000L + new Random().nextInt(900000000);
        CreateLoanCommand createLoanCommand = CreateLoanCommand.builder()
                .loanNumber(randomLoanNumber)
                .mobileNumber(mobileNumber).loanType(LoanConstants.HOME_LOAN)
                .totalLoan(LoanConstants.NEW_LOAN_LIMIT).amountPaid(0)
                .outstandingAmount(LoanConstants.NEW_LOAN_LIMIT).isActive(LoanConstants.ACTIVE_SW).build();
        commandGateway.sendAndWait(createLoanCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
    }

    @Operation(summary = "Update Loan Details REST API", description = "REST API to update loan details based on a loan number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateLoan(@Valid @RequestBody LoansDTO loansDTO) {
        UpdateLoanCommand updateLoanCommand = UpdateLoanCommand.builder()
                .loanNumber(loansDTO.getLoanNumber())
                .mobileNumber(loansDTO.getMobileNumber())
                .loanType(LoanConstants.HOME_LOAN)
                .totalLoan(loansDTO.getTotalLoan())
                .amountPaid(loansDTO.getAmountPaid())
                .outstandingAmount(loansDTO.getOutstandingAmount())
                .isActive(LoanConstants.ACTIVE_SW).build();
        commandGateway.sendAndWait(updateLoanCommand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
    }

    @Operation(summary = "Delete Loan Details REST API", description = "REST API to delete Loan details based on a mobile number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PatchMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoan(@RequestParam("loanNumber") Long loanNumber) {
        DeleteLoanCommand deleteLoanCommand = DeleteLoanCommand.builder()
                .loanNumber(loanNumber).isActive(LoanConstants.IN_ACTIVE_SW).build();
        commandGateway.sendAndWait(deleteLoanCommand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
    }
}
