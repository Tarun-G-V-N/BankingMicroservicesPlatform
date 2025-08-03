package com.smartbank.accountservice.command.controller;

import com.smartbank.accountservice.command.CreateAccountCommand;
import com.smartbank.accountservice.command.DeleteAccountCommand;
import com.smartbank.accountservice.command.UpdateAccountCommand;
import com.smartbank.accountservice.constants.AccountConstants;
import com.smartbank.accountservice.dtos.AccountsDTO;
import com.smartbank.accountservice.dtos.ErrorResponseDTO;
import com.smartbank.accountservice.dtos.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Tag(
        name = " CRUD REST APIs for Account Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Account Microservice"
)
@RestController
@RequestMapping(path="/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;

    @Operation(summary = "Create Account", description = "Create a new account for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "500", description = "Account creation failed. Please try again or contact Dev team", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestParam("mobileNumber")
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
                .mobileNumber(mobileNumber)
                .accountNumber(randomAccNumber)
                .accountType(AccountConstants.SAVINGS)
                .branchAddress(AccountConstants.ADDRESS)
                .isActive(AccountConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(createAccountCommand);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(summary = "Update Account", description = "Update account details for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details updated successfully"),
            @ApiResponse(responseCode = "417", description = "Update operation failed. Please try again or contact Dev team")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@RequestBody @Valid AccountsDTO accountsDTO) {
        UpdateAccountCommand updateAccountCommand = UpdateAccountCommand.builder()
                .accountNumber(accountsDTO.getAccountNumber())
                .accountType(accountsDTO.getAccountType())
                .branchAddress(accountsDTO.getBranchAddress())
                .mobileNumber(accountsDTO.getMobileNumber())
                .isActive(AccountConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(updateAccountCommand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    }

    @Operation(summary = "Delete Account", description = "Delete account details for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Delete operation failed. Please try again or contact Dev team")
    })
    @PatchMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam("accountNumber") Long accountNumber) {
        DeleteAccountCommand deleteAccountCommand = DeleteAccountCommand.builder()
                .accountNumber(accountNumber).isActive(AccountConstants.IN_ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(deleteAccountCommand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    }
}
