package com.smartbank.accountservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Account", description = "Accounts details")
public class AccountsDTO {
    @Schema(description = "Account number", example = "1234567890")
    @Pattern(regexp = "$|[0-9]{10}", message = "Account number should be 10 digits")
    private Long accountNumber;
    @NotEmpty(message = "Account type cannot be empty")
    @Schema(description = "Account type", example = "Savings")
    private String accountType;
    @NotEmpty(message = "Branch address cannot be empty")
    @Schema(description = "Branch address", example = "123 Main St, Anytown, USA")
    private String branchAddress;
    @Schema(description = "Mobile number", example = "9994488675")
    @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number should be 10 digits")
    private String mobileNumber;
    private boolean isActive;
}
