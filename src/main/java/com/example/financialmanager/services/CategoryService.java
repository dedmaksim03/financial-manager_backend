package com.example.financialmanager.services;

import com.example.financialmanager.entities.Category;
import com.example.financialmanager.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).get();
    }
}
