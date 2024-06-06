package com.example.financialmanager.services;

import com.example.financialmanager.entities.ActionType;
import com.example.financialmanager.repositories.ActionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionTypeService {
    private final ActionTypeRepository actionTypeRepository;

    public ActionType getActionTypeById(Short id){
        return actionTypeRepository.findById(id).get();
    }
}
