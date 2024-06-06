package com.example.financialmanager.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
