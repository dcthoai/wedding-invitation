package com.example.repository;

import com.example.entity.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<CustomUserDetails, Integer> {
    Optional<CustomUserDetails> findByUsername(String username);
}
