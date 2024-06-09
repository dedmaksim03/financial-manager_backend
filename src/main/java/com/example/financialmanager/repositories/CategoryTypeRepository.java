package com.example.financialmanager.repositories;

import com.example.financialmanager.entities.CategoryType;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Short> {

    @Override
    @Nonnull
    Optional<CategoryType> findById(@Nonnull Short id);
}
