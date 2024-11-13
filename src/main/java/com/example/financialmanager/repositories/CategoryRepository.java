package com.example.financialmanager.repositories;

import com.example.financialmanager.entities.Category;
import com.example.financialmanager.entities.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @Nonnull
    Optional<Category> findById(@Nonnull Long id);

    @Query("select cat from Category cat where cat.user = :user")
    List<Category> getCategoriesByUser (@Param("user") User user);
}
