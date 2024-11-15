package com.example.financialmanager.services;

import com.example.financialmanager.dtos.AppError;
import com.example.financialmanager.dtos.JwtRequest;
import com.example.financialmanager.dtos.JwtResponse;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.repositories.RefreshTokenRepository;
import com.example.financialmanager.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${jwt.lifetime.refresh_token}")
    private Duration jwtLifeRefreshTime;

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<?> createAuthToken(JwtRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        String refreshToken = createRefreshToken(userService.findByUsername(authRequest.getUsername()).get());

        HttpHeaders headers = new HttpHeaders();
        ResponseCookie newRefreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true) // Только для HTTP
//                .secure(true) // Использовать только через HTTPS
                    .path("/") // Доступно на всех страницах сайта
                .maxAge(jwtLifeRefreshTime.toSeconds()) // Время жизни в секундах
//                .sameSite("Strict") // Ограничить использование между доменами
                .build();

        headers.add(HttpHeaders.SET_COOKIE, newRefreshCookie.toString());
//        headers.add("Access-Control-Allow-Credentials", "true");
//        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
        return new ResponseEntity<>(new JwtResponse(token, refreshToken), headers, HttpStatus.OK);
    }

    public ResponseEntity<?> logout(Principal principal){
        try {
            User user = userService.findByUsername(principal.getName()).get();

            HttpHeaders headers = new HttpHeaders();

            // Удаляем старый refresh токен из куков
            ResponseCookie oldRefreshCookie = ResponseCookie.from("refresh_token", "")
                    .httpOnly(true)
//                    .secure(true)
                    .path("/")
                    .maxAge(0) // Сбрасываем время жизни до нуля, чтобы удалить куки
                    .sameSite("Strict")
                    .build();

            headers.add(HttpHeaders.SET_COOKIE, oldRefreshCookie.toString());

            refreshTokenService.deleteByUser(user);

            Map<String, Object> response = new HashMap<>();
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public String createAuthToken(User user){
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        return jwtTokenUtils.generateToken(userDetails);
    }

    /** * Создает новый refresh токен для указанного пользователя. */
    public String createRefreshToken(User user) {
        return refreshTokenService.createRefreshToken(user);
    }

    public User validateRefreshToken(String token) {
        return refreshTokenService.validateRefreshToken(token);
    }

    /** * Удаление refresh токена после его использования. * * @param token - токен, который нужно удалить. */
    @Transactional
    public void deleteByToken(String token) {
        refreshTokenService.deleteByToken(token);
    }
}
