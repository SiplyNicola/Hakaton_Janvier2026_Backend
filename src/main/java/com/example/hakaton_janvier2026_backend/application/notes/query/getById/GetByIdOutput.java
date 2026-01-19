package com.example.hakaton_janvier2026_backend.application.notes.query.getById;

import java.time.LocalDateTime;

public class GetByIdOutput {
    public int id;

    public int owner_id;
    public int folder_id;

    public String title;
    public String content_markdown;

    public LocalDateTime created_at;
    public LocalDateTime updated_at;

    public long size_bytes;
    public int line_count;
    public int word_count;
    public int char_count;
}
