package com.example.financialmanager.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categories_types")
@Data
public class CategoryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "name")
    private String name;
}
