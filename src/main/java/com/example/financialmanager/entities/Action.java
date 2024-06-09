package com.example.financialmanager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "actions")
@Data
@NoArgsConstructor
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Action(Date date, Double sum, String message, Category category, Account account, User user){
        this.date = date;
        this.sum = sum;
        this.message = message;
        this.account = account;
        this.category = category;
        this.user = user;
    }
}
