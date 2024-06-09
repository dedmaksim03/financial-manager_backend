package com.example.financialmanager.services;

import com.example.financialmanager.entities.CategoryType;
import com.example.financialmanager.repositories.CategoryTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryTypeService {
    private final CategoryTypeRepository categoryTypeRepository;

    public CategoryType getActionTypeById(Short id){
        return categoryTypeRepository.findById(id).get();
    }
}
