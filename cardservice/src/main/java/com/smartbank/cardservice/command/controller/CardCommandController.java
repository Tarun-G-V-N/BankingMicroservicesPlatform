package com.smartbank.cardservice.command.controller;

import com.smartbank.cardservice.command.CreateCardCommand;
import com.smartbank.cardservice.command.DeleteCardCommand;
import com.smartbank.cardservice.command.UpdateCardCommand;
import com.smartbank.cardservice.constants.CardConstants;
import com.smartbank.cardservice.dtos.CardDTO;
import com.smartbank.cardservice.dtos.ErrorResponseDTO;
import com.smartbank.cardservice.dtos.ResponseDTO;
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
        name = " CRUD REST APIs for Card Microservice",
        description = "CREATE, READ, UPDATE, DELETE APIs for Card Microservice"
)
@RestController
@RequestMapping("/cards")
@AllArgsConstructor
@Validated
public class CardCommandController {
    private final CommandGateway commandGateway;

    @Operation(summary = "Create Card REST API", description = "REST API to create new Card inside EazyBank")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCard(@RequestParam("mobileNumber") @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber) {
        long randomCardNumber = 1000000000L + new Random().nextInt(900000000);
        CreateCardCommand createCardCommand = CreateCardCommand.builder()
                .cardNumber(randomCardNumber)
                .mobileNumber(mobileNumber)
                .cardType(CardConstants.CREDIT_CARD)
                .totalLimit(CardConstants.NEW_CARD_LIMIT)
                .amountUsed(0)
                .availableAmount(CardConstants.NEW_CARD_LIMIT)
                .isActive(CardConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(createCardCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(CardConstants.STATUS_201, CardConstants.MESSAGE_201));
    }

    @Operation(summary = "Update Card Details REST API", description = "REST API to update card details based on a card number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateCard(@RequestBody @Valid CardDTO cardDto) {
        UpdateCardCommand updateCardCommand = UpdateCardCommand.builder()
                .cardNumber(cardDto.getCardNumber())
                .mobileNumber(cardDto.getMobileNumber())
                .cardType(cardDto.getCardType())
                .totalLimit(cardDto.getTotalLimit())
                .amountUsed(cardDto.getAmountUsed())
                .availableAmount(cardDto.getAvailableAmount())
                .isActive(CardConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(updateCardCommand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
    }

    @Operation(summary = "Delete Card Details REST API", description = "REST API to delete Card details based on a mobile number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PatchMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCard(@RequestParam("cardNumber") Long cardNumber) {
        DeleteCardCommand deleteCardCommand = DeleteCardCommand.builder()
                .cardNumber(cardNumber)
                .isActive(CardConstants.IN_ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(deleteCardCommand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
    }
}
