package com.smartbank.loanservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Response object for error responses")
public class ErrorResponseDTO {
    @Schema(description = "API path where the error occurred")
    private String apiPath;
    @Schema(description = "HTTP status code")
    private HttpStatus errorCode;
    @Schema(description = "Error message")
    private String errorMessage;
    @Schema(description = "Time when the error occurred")
    private LocalDateTime errorTime;
}