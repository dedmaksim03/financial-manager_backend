package com.example.financialmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    private String name;

    private String categoryType;

    private Float sumCategory;

    private String categoryColor;
}
