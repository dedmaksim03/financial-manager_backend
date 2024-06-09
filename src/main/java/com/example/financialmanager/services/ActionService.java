package com.example.financialmanager.services;

import com.example.financialmanager.dtos.ActionDto;
import com.example.financialmanager.entities.Action;
import com.example.financialmanager.entities.Category;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.repositories.ActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;
    private final CategoryTypeService categoryTypeService;
    private final CategoryService categoryService;
    private final AccountSerivce accountSerivce;
    private final UserService userService;

    public List<Action> getActionsByUser(User user){
        return actionRepository.findAllByUser(user);
    }

    public List<ActionDto> getActionDtoListByUser(User user){
         return getActionsByUser(user).stream().map(action -> new ActionDto(
                 action.getId(),
                 action.getDate(),
                 action.getSum(),
                 action.getMessage(),
                 action.getCategory().getId(),
                 action.getCategory().getName(),
                 action.getAccount().getId(),
                 action.getAccount().getName(),
                 null)).collect(Collectors.toList());
    }

    public Action save(Action action){
        return actionRepository.save(action);
    }

    public ActionDto save(ActionDto actionDto, User user){
        System.out.println(actionDto.getDate().toString() +
                actionDto.getSum().toString() +
                actionDto.getMessage() +
                actionDto.getCategoryId().toString() +
                actionDto.getAccountId().toString());
        Action newAction = this.save(new Action(
                actionDto.getDate(),
                actionDto.getSum(),
                actionDto.getMessage(),
                categoryService.getCategoryById(actionDto.getCategoryId()),
                accountSerivce.getAccountById(actionDto.getAccountId()),
                user
        ));
        return new ActionDto(
                newAction.getId(),
                newAction.getDate(),
                newAction.getSum(),
                newAction.getMessage(),
                newAction.getCategory().getId(),
                newAction.getCategory().getName(),
                newAction.getAccount().getId(),
                newAction.getAccount().getName(),
                null);
    }
}
