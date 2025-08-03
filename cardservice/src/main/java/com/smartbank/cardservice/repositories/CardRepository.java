package com.smartbank.cardservice.repositories;

import com.smartbank.cardservice.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByMobileNumberAndIsActive(String mobileNumber, boolean isActive);
    Optional<Card> findByCardNumberAndIsActive(Long cardNumber, boolean isActive);
}
