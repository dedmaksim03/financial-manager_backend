package com.example.financialmanager.controllers;

import com.example.financialmanager.dtos.DataDto;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.services.ActionService;
import com.example.financialmanager.services.CategoryService;
import com.example.financialmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users/")
@RequiredArgsConstructor
@CrossOrigin
public class UserGetDataController {
    private final UserService userService;
    private final ActionService actionService;
    private final CategoryService categoryService;

    @GetMapping("/info")
    public ResponseEntity<?> getActions(Principal principal){
        User user = userService.findByUsername(principal.getName()).get();
        System.out.println(user.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        headers.add("Access-Control-Max-Age", "3600");
        headers.add("Access-Control-Allow-Headers", "x-requested-with");
        return new ResponseEntity<>(new DataDto(user.getUsername(), actionService.getActionDtoListByUser(user)), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(Principal principal){
        User user = userService.findByUsername(principal.getName()).get();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        headers.add("Access-Control-Max-Age", "3600");
        headers.add("Access-Control-Allow-Headers", "x-requested-with");
        return new ResponseEntity<>(actionService.getCategoryDtoListByUser(user), HttpStatus.OK);
//        return new ResponseEntity<>(new CategoriesDto(categoryService.getCategoryDtoListByUser(user)), HttpStatus.OK);
    }
}
