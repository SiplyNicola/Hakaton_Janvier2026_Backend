package com.example.hakaton_janvier2026_backend.infrastructure.notes;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "Notes")
public class DbNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    //Foreign key


    //Data
    @Column(length = 150)
    public String title;
    public String content_markdown;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;

    //Metadata
    public long size_bytes;
    public int line_count;
    public int word_count;
    public int char_count;
}
