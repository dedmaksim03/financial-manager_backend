package com.example.financialmanager.services;

import com.example.financialmanager.dtos.ActionDto;
import com.example.financialmanager.dtos.CategoryDto;
import com.example.financialmanager.entities.Action;
import com.example.financialmanager.entities.Category;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.repositories.ActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public List<CategoryDto> getCategoryDtoListByUser(User user){
        Map<Category, Float> categories = new HashMap<>();
        List<Category> categoriesList = categoryService.getCategoriesByUser(user);
        for (Category category: categoriesList){
            categories.put(category, 0f);
        }
        List<Action> actions = getActionsByUser(user);
        for (Action action: actions){
            double newSum = categories.get(action.getCategory()) + action.getSum();
            categories.put(action.getCategory(), (float) newSum);
        }
        List<CategoryDto> listCategoryDto = new ArrayList<>();
        for (Category category: categories.keySet()){
            listCategoryDto.add(new CategoryDto(
                    category.getId(),
                    category.getName(),
                    category.getCategoryType().getName(),
                    categories.get(category)
            ));
        }
        return listCategoryDto;
    }
}
