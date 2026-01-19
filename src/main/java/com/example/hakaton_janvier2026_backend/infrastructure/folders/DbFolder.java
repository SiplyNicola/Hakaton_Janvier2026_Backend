package com.example.hakaton_janvier2026_backend.infrastructure.folders;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "Folders")
public class DbFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // Foreign key
    

    //Data
    private String title;
    private LocalDateTime created_at;
}
