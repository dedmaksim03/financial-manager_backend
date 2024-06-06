package com.example.financialmanager.repositories;

import com.example.financialmanager.entities.ActionType;
import com.example.financialmanager.entities.Category;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @Nonnull
    Optional<Category> findById(@Nonnull Long id);
}
