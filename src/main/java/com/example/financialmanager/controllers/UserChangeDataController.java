package com.example.financialmanager.controllers;

import com.example.financialmanager.dtos.ActionDto;
import com.example.financialmanager.dtos.DataDto;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.services.ActionService;
import com.example.financialmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users/")
@RequiredArgsConstructor
public class UserChangeDataController {
    private final UserService userService;
    private final ActionService actionService;

    @PostMapping("/put")
    public ResponseEntity<?> putAction(@RequestBody ActionDto actionDto, Principal principal){
        return new ResponseEntity<>(actionService.save(actionDto, userService.findByUsername(principal.getName()).get()), HttpStatus.OK);
    }
}
