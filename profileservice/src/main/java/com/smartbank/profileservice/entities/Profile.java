package com.smartbank.profileservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private long profileId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "mobile_number", length = 20, nullable = false)
    private String mobileNumber;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @Column(name = "account_number")
    private long accountNumber;

    @Column(name = "card_number")
    private long cardNumber;

    @Column(name = "loan_number")
    private long loanNumber;
}
