package com.example.financialmanager.services;

import com.example.financialmanager.dtos.ActionDto;
import com.example.financialmanager.dtos.CategoryDto;
import com.example.financialmanager.entities.Category;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.repositories.CategoryRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).get();
    }
    public List<Category> getCategoriesByUser (User user) {
        return categoryRepository.getCategoriesByUser(user);
    }
}
