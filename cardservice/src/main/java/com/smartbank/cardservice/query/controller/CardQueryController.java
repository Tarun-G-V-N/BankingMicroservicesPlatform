package com.smartbank.cardservice.query.controller;

import com.smartbank.cardservice.dtos.CardDTO;
import com.smartbank.cardservice.dtos.ErrorResponseDTO;
import com.smartbank.cardservice.query.FindCardQuery;
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
        name = " CRUD REST APIs for Card Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Card Microservice"
)
@RestController
@RequestMapping("/cards")
@AllArgsConstructor
@Validated
public class CardQueryController {
    private static final Logger logger = LoggerFactory.getLogger(CardQueryController.class);
    private final QueryGateway queryGateway;

    @Operation(summary = "Fetch Card Details REST API", description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/fetch")
    public ResponseEntity<CardDTO> fetchCard(@RequestParam("mobileNumber") @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber) {
        logger.debug("Fetch Card details method start");
        CardDTO cardDto = queryGateway.query(new FindCardQuery(mobileNumber), CardDTO.class).join();
        logger.debug("Fetch Card details method end");
        return ResponseEntity.status(HttpStatus.OK).body(cardDto);
    }
}
