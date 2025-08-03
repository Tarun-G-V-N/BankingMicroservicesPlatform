package com.smartbank.loanservice.query.controller;

import com.smartbank.loanservice.controllers.LoanController;
import com.smartbank.loanservice.dtos.ErrorResponseDTO;
import com.smartbank.loanservice.dtos.LoansDTO;
import com.smartbank.loanservice.query.FindLoanQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = " CRUD REST APIs for Loan Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Loan Microservice"
)
@RestController
@RequestMapping("/loans")
@AllArgsConstructor
@Validated
public class LoanQueryController {
    private static final Logger logger = LoggerFactory.getLogger(LoanQueryController.class);
    private final QueryGateway queryGateway;

    @Operation(summary = "Fetch Loan Details REST API", description = "REST API to fetch loan details based on a mobile number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/fetch")
    public ResponseEntity<LoansDTO> fetchLoan(@RequestHeader("smartBank-correlation-id") String correlationId, @RequestParam("mobileNumber") @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber) {
        logger.debug("Fetch Loan details method start");
        LoansDTO loansDTO = queryGateway.query(new FindLoanQuery(mobileNumber), LoansDTO.class).join();
        logger.debug("Fetch Loan details method end");
        return ResponseEntity.status(HttpStatus.OK).body(loansDTO);
    }
}
