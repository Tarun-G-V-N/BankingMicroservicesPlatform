package com.smartbank.customerservice.controllers;

import com.smartbank.customerservice.dtos.*;
import com.smartbank.customerservice.services.ICustomerService;
import com.smartbank.customerservice.constants.CustomerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = " CRUD REST APIs for Customer Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Customer Microservice"
)
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@Validated
public class CustomerController {
    private ContactInfoDTO contactInfoDTO;
    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"
            ),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfoDTO> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(contactInfoDTO);
    }
}
