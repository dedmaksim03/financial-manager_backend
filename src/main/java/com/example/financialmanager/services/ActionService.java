package com.example.financialmanager.services;

import com.example.financialmanager.dtos.ActionDto;
import com.example.financialmanager.entities.Action;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.repositories.ActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;

    public List<Action> getActionsByUser(User user){
        return actionRepository.findAllByUser(user);
    }

    public List<ActionDto> getActionDtoListByUser(User user){
         return getActionsByUser(user).stream().map(Action -> new ActionDto(Action.getId(), Action.getDate(), Action.getSum(), Action.getMessage())).collect(Collectors.toList());
    }
}
