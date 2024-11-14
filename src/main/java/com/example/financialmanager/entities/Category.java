package com.example.financialmanager.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Optional;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "root_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CategoryType categoryType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Optional<Category> getCategory() {
        return Optional.ofNullable(category);
    }
}
