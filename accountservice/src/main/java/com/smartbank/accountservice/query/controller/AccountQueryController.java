package com.smartbank.accountservice.query.controller;

import com.smartbank.accountservice.dtos.AccountsDTO;
import com.smartbank.accountservice.dtos.ErrorResponseDTO;
import com.smartbank.accountservice.query.FindAccountQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = " CRUD REST APIs for Account Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Account Microservice"
)
@RestController
@RequestMapping(path="/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountQueryController {
    private final QueryGateway queryGateway;
    @Operation(summary = "Fetch Account", description = "Fetch account details for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to fetch account . Please try again or contact Dev team", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<AccountsDTO> fetchAccountDetails(@RequestParam("mobileNumber") @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number should be 10 digits") String mobileNumber) {
        FindAccountQuery findAccountQuery = new FindAccountQuery(mobileNumber);
        AccountsDTO accountsDTO = queryGateway.query(findAccountQuery, AccountsDTO.class).join();
        return ResponseEntity.status(HttpStatus.OK).body(accountsDTO);
    }
}
