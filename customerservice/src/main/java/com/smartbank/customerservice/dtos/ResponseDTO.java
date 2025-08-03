package com.smartbank.customerservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Response", description = "Response object")
public class ResponseDTO {
    @Schema(description = "Status code")
    private String statusCode;
    @Schema(description = "Status message")
    private String message;
}
