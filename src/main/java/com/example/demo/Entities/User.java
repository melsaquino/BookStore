package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(nullable = false)
    private int id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String email;

    @Setter
    @Getter
    @Column(nullable = false)
    private String password;

}
