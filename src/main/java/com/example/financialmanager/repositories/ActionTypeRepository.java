package com.example.financialmanager.repositories;

import com.example.financialmanager.entities.ActionType;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Short> {

    @Override
    @Nonnull
    Optional<ActionType> findById(@Nonnull Short id);
}
