package com.smartbank.profileservice.queries.controller;

import com.smartbank.profileservice.dtos.ProfileDTO;
import com.smartbank.profileservice.dtos.ErrorResponseDTO;
import com.smartbank.profileservice.queries.FindProfileQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = " CRUD REST APIs for Profile Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Profile Microservice"
)
@RestController
@RequestMapping(path = "/profiles", produces = "application/json")
@AllArgsConstructor
@Validated
public class ProfileQueryController {

    private final QueryGateway queryGateway;

    @Operation(summary = "Fetch Profile", description = "Fetch Profile details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile details fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to fetch Profile. Please try again or contact Dev team", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<ProfileDTO> fetchProfileDetails(@RequestParam("mobileNumber")
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        FindProfileQuery findProfileQuery = new FindProfileQuery(mobileNumber);
        ProfileDTO ProfileDTO = queryGateway.query(findProfileQuery, ProfileDTO.class).join();
        return ResponseEntity.status(HttpStatus.OK).body(ProfileDTO);
    }
}
