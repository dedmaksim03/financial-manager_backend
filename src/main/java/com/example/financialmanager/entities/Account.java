package com.example.financialmanager.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sum")
    private Double sum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
