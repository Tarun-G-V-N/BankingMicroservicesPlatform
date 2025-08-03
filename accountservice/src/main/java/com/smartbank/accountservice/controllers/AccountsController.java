package com.smartbank.accountservice.controllers;

import com.smartbank.accountservice.constants.AccountConstants;
import com.smartbank.accountservice.dtos.*;
import com.smartbank.accountservice.services.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = " CRUD REST APIs for Account Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Account Microservice"
)
@RestController
@RequestMapping(path="/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {
    private final IAccountService iAccountService;
    @Autowired
    private ContactInfoDTO contactInfoDTO;
    @Value("${build.version}")
    private String buildInfo;
    public AccountsController(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

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

    @Operation(summary = "Get Build Info", description = "Fetch Build Version"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/build-version")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildInfo);
    }
}
