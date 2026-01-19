package com.example.hakaton_janvier2026_backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Folder {
    //ID
    private int id;

    //Foreign key
    private int parent_id;
    private int owner_id;

    //Data
    private String name;
    private LocalDateTime created_at;
}
