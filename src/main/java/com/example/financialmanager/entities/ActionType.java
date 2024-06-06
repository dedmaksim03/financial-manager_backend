package com.example.financialmanager.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "types")
@Data
public class ActionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "name")
    private String name;
}
