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
@Schema(name = "Customer details", description = "Customer details along with his/her Accounts, Cards & Loans details")
public class CustomerDetailsDTO {
    private CustomerDTO customerDTO;
    private AccountsDTO accountsDTO;
    private CardDTO cardsDTO;
    private LoansDTO loansDTO;
}
