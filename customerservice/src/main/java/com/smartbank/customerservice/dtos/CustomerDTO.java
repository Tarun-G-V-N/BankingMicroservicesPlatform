package com.smartbank.customerservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Customer", description = "Customer details")
public class CustomerDTO {
    private String customerId;
    @NotEmpty(message = "Name should not be null or empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    @Schema(description = "Name of the customer", example = "Tarun")
    private String name;
    @NotEmpty(message = "Name should not be null or empty")
    @Email(message = "Invalid email address")
    @Schema(description = "Email of the customer", example = "7V9t0@example.com")
    private String email;
    @NotEmpty(message = "Name should not be null or empty")
    @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number should be 10 digits")
    @Schema(description = "Mobile number of the customer", example = "1234567890")
    private String mobileNumber;
    private boolean isActive;
}
