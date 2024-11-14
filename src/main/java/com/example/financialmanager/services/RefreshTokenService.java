package com.example.financialmanager.services;

import com.example.financialmanager.entities.RefreshToken;
import com.example.financialmanager.entities.User;
import com.example.financialmanager.repositories.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.lifetime.refresh_token}")
    private Duration jwtLifeRefreshTime;

    private final RefreshTokenRepository refreshTokenRepository;


    /** * Проверка валидности refresh токена. * * @param token - Токен для проверки. * @return True если токен действителен, иначе False. */
    @Transactional
    public User validateRefreshToken(String token) {

        RefreshToken rt = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (rt.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(rt);
            throw new RuntimeException("Expired refresh token");
        }

        return rt.getUser();
    }

    /** * Создает новый refresh токен для указанного пользователя. */
    public String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString(); // Генерация уникального токена
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plusSeconds(jwtLifeRefreshTime.toSeconds());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setCreatedAt(now);
        refreshToken.setExpiresAt(expiryTime);
        refreshToken.setUser(user);
        System.out.println(refreshToken);
        System.out.println(refreshTokenRepository.save(refreshToken));
        refreshTokenRepository.flush();

        return token;
    }

    public void deleteByToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteByUser(User user){refreshTokenRepository.deleteByUser(user);}
}
