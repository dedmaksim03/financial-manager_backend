package com.example.financialmanager.repositories;

import com.example.financialmanager.entities.Account;
import com.example.financialmanager.entities.Category;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Override
    @Nonnull
    Optional<Account> findById(@Nonnull Long id);
}
