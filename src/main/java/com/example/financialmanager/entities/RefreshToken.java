package com.example.financialmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Constraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "token")
    private String token;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "expires_date")
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
