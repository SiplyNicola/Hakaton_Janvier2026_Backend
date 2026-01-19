package com.example.hakaton_janvier2026_backend.infrastructure.users;

import jakarta.persistence.*;

@Entity
@Table(name= "Users")
public class DbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String username;
    public String password;
}
