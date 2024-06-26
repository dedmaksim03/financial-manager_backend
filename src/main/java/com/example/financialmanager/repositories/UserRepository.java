package com.example.financialmanager.repositories;

import com.example.financialmanager.entities.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Nonnull
    Optional<User> findById(@Nonnull Long id);
}
