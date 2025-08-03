package com.smartbank.customerservice.commands.controllers;

import com.smartbank.common.commands.UpdateCustomerMobileNumberCommand;
import com.smartbank.common.dto.CustomerMobileNumberUpdateDTO;
import com.smartbank.customerservice.commands.CreateCustomerCommand;
import com.smartbank.customerservice.commands.DeleteCustomerCommand;
import com.smartbank.customerservice.commands.UpdateCustomerCommand;
import com.smartbank.customerservice.constants.CustomerConstants;
import com.smartbank.customerservice.dtos.CustomerDTO;
import com.smartbank.customerservice.dtos.ErrorResponseDTO;
import com.smartbank.customerservice.dtos.ResponseDTO;
import com.smartbank.customerservice.queries.FindCustomerQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.util.UUID;

@Tag(
        name = " CRUD REST APIs for Customer Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Customer Microservice"
)
@RestController
@RequestMapping(path = "/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CustomerCommandController {

    private  final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Operation(summary = "Create Customer", description = "Create a new customer entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "500", description = "Customer creation failed. Please try again or contact Dev team", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCustomer(@Valid @RequestBody CustomerDTO CustomerDTO) {
        CreateCustomerCommand createCustomerCommand = CreateCustomerCommand.builder()
                .customerId(UUID.randomUUID().toString())
                .name(CustomerDTO.getName())
                .email(CustomerDTO.getEmail())
                .mobileNumber(CustomerDTO.getMobileNumber())
                .isActive(CustomerConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(createCustomerCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(CustomerConstants.STATUS_201, CustomerConstants.MESSAGE_201));
    }

    @Operation(summary = "Update Customer", description = "Update customer details for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer details updated successfully"),
            @ApiResponse(responseCode = "500", description = "Update operation failed. Please try again or contact Dev team", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateCustomerDetails(@Valid @RequestBody CustomerDTO CustomerDTO) {
        UpdateCustomerCommand updateCustomerCommand = UpdateCustomerCommand.builder()
                .customerId(CustomerDTO.getCustomerId())
                .name(CustomerDTO.getName())
                .email(CustomerDTO.getEmail())
                .mobileNumber(CustomerDTO.getMobileNumber())
                .isActive(CustomerConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(updateCustomerCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }

    @Operation(summary = "Delete Customer", description = "Delete customer details of a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer details deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Delete operation failed. Please try again or contact Dev team")
    })
    @PatchMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCustomer(@RequestParam("customerId")
                                                          @Pattern(regexp = "(^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$)",
                                                                  message = "CustomerId is invalid") String customerId) {
        DeleteCustomerCommand deleteCustomerCommand = DeleteCustomerCommand.builder()
                .customerId(customerId)
                .isActive(CustomerConstants.IN_ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(deleteCustomerCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }

    @Operation(summary = "Update Customer Mobile Number", description = "Update mobile number of a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer mobile number updated successfully"),
            @ApiResponse(responseCode = "417", description = "Delete operation failed. Please try again or contact Dev team")
    })
    @PatchMapping("/mobile-number")
    public ResponseEntity<ResponseDTO> updateMobileNumber(@Valid@RequestBody CustomerMobileNumberUpdateDTO customerMobileNumberUpdateDTO) {
        UpdateCustomerMobileNumberCommand updateCustomerMobileNumberCommand = UpdateCustomerMobileNumberCommand.builder()
                .customerId(customerMobileNumberUpdateDTO.getCustomerId())
                .accountNumber(customerMobileNumberUpdateDTO.getAccountNumber())
                .loanNumber(customerMobileNumberUpdateDTO.getLoanNumber())
                .cardNumber(customerMobileNumberUpdateDTO.getCardNumber())
                .mobileNumber(customerMobileNumberUpdateDTO.getMobileNumber())
                .newMobileNumber(customerMobileNumberUpdateDTO.getNewMobileNumber())
                .build();

        try(SubscriptionQueryResult<ResponseDTO, ResponseDTO> queryResult = queryGateway.subscriptionQuery(new FindCustomerQuery(customerMobileNumberUpdateDTO.getMobileNumber()), ResponseDTO.class, ResponseDTO.class)) {
            commandGateway.send(updateCustomerMobileNumberCommand, new CommandCallback<>() {
                @Override
                public void onResult(@Nonnull CommandMessage<? extends UpdateCustomerMobileNumberCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                    if(commandResultMessage.isExceptional()) {
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ResponseDTO(CustomerConstants.STATUS_500, commandResultMessage.exceptionResult().getMessage()));
                    }
                }
            });
            return ResponseEntity.status(HttpStatus.OK)
                    .body(queryResult.updates().blockFirst());
        }
    }
}
