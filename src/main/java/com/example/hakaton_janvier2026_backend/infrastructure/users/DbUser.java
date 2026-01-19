package com.example.hakaton_janvier2026_backend.infrastructure.users;

import jakarta.persistence.*;

@Entity
@Table(name= "users")
public class DbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String username;
    public String password;
}
