package com.example.financialmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users/")
public class UserInfoController {

    @GetMapping("/info")
    public ResponseEntity<String> getInfo(Principal principal){
        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
    }
}
