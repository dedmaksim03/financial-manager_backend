package com.example.financialmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DataDto {
    private String username;
    private List<?> data;
}
