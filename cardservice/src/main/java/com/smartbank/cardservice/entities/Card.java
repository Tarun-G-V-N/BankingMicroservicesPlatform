package com.smartbank.cardservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "cards")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Card extends BaseEntity {

    @Id
    private Long cardNumber;

    private String mobileNumber;

    private boolean isActive;

    private String cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;

    private boolean communicationSent;

}
