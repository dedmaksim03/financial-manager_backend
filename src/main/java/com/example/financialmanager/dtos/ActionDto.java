package com.example.financialmanager.dtos;

import com.example.financialmanager.entities.Account;
import com.example.financialmanager.entities.ActionType;
import com.example.financialmanager.entities.Category;
import com.example.financialmanager.entities.User;
import jakarta.persistence.*;
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

}
