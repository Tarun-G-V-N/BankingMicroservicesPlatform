package com.smartbank.profileservice.repositories;

import com.smartbank.profileservice.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByMobileNumberAndIsActive(String mobileNumber, boolean isActive);
}
