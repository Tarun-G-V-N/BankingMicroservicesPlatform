package com.smartbank.customerservice.queries.controller;

import com.smartbank.customerservice.dtos.CustomerDTO;
import com.smartbank.customerservice.dtos.ErrorResponseDTO;
import com.smartbank.customerservice.queries.FindCustomerQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = " CRUD REST APIs for Customer Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Customer Microservice"
)
@RestController
@RequestMapping(path = "/customers", produces = "application/json")
@AllArgsConstructor
@Validated
public class CustomerQueryController {

    private final QueryGateway queryGateway;

    @Operation(summary = "Fetch Customer", description = "Fetch customer details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer details fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to fetch customer. Please try again or contact Dev team", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchCustomerDetails(@RequestParam("mobileNumber")
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        FindCustomerQuery findCustomerQuery = new FindCustomerQuery(mobileNumber);
        CustomerDTO customerDTO = queryGateway.query(findCustomerQuery, CustomerDTO.class).join();
        return ResponseEntity.status(org.springframework.http.HttpStatus.OK).body(customerDTO);
    }
}
