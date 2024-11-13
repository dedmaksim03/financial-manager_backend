package com.example.financialmanager.controllers;

import com.example.financialmanager.dtos.JwtRequest;
import com.example.financialmanager.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@CrossOrigin/*(origins = {"http://192.168.0.73:3000", "http://localhost:3000"})*/
public class AuthController {
    @Value("${jwt.lifetime.refresh_token}")
    private Duration jwtLifeRefreshTime;

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody JwtRequest jwtRequest){
        System.out.println(jwtRequest.toString());
        return authService.createAuthToken(jwtRequest);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws BadRequestException {
        String refreshToken = extractRefreshTokenFromCookies(request);

        if (refreshToken == null) {
            throw new BadRequestException("Missing refresh token");
        }

        try {
            // Получаем информацию о пользователе из базы данных
            User user = authService.validateRefreshToken(refreshToken); // Загружаем пользователя по refresh токену

            String accessToken = authService.createAuthToken(user);
            String newRefreshToken = authService.createRefreshToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("access_token", accessToken);

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

            // Устанавливаем новый refresh токен в куки
            ResponseCookie newRefreshCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                    .httpOnly(true) // Только для HTTP
//                    .secure(true) // Использовать только через HTTPS
                    .path("/") // Доступно на всех страницах сайта
                    .maxAge(jwtLifeRefreshTime.toSeconds()) // Время жизни в секундах
                    .sameSite("Strict") // Ограничить использование между доменами
                    .build();

            headers.add(HttpHeaders.SET_COOKIE, newRefreshCookie.toString());

            authService.deleteByToken(refreshToken);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
//            throw new UnauthorizedException(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        System.out.println(Arrays.toString(cookies));
        return null;
    }
}
