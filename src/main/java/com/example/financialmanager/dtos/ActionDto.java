package com.example.financialmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ActionDto {

    private Long id;

    private Date date;

    private Double sum;

    private String message;

    private Long categoryId;

    private String category;

    private Long accountId;

    private String account;

    private Long userId;

}
