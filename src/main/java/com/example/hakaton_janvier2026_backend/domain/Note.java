package com.example.hakaton_janvier2026_backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class Note {
    //Id
    private int id;

    // Foreign key
    private int owner_id;
    private int folder_id;

    //Data
    private String title;
    private String content_markdown;
    private boolean isWriteMode;

    // Generated date by the database
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;
}
