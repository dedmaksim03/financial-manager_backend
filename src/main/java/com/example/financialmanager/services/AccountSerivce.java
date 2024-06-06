package com.example.financialmanager.services;

import com.example.financialmanager.entities.Account;
import com.example.financialmanager.entities.Category;
import com.example.financialmanager.repositories.AccountRepository;
import com.example.financialmanager.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountSerivce {
    private final AccountRepository accountRepository;

    public Account getAccountById(Long id){
        return accountRepository.findById(id).get();
    }
}
