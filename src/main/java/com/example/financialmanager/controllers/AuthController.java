package com.example.financialmanager.controllers;

import com.example.financialmanager.dtos.JwtRequest;
import com.example.financialmanager.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@CrossOrigin/*(origins = {"http://192.168.0.73:3000", "http://localhost:3000"})*/
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody JwtRequest jwtRequest){
        System.out.println(jwtRequest.toString());
        return authService.createAuthToken(jwtRequest);
    }
}
