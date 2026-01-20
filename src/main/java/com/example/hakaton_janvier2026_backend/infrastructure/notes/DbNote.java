package com.example.hakaton_janvier2026_backend.infrastructure.notes;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.users.DbUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "notes")
public class DbNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    // Foreign Key: owner_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    public DbUser owner;

    // Foreign Key: folder_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id") // Peut être NULL selon votre SQL
    public DbFolder folder;

    @Column(length = 150)
    public String title;

    @Column(columnDefinition = "LONGTEXT") // Pour correspondre au SQL
    public String content_markdown;

    public LocalDateTime created_at;
    public LocalDateTime updated_at;
    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;

    public long size_bytes;
    public int line_count;
    public int word_count;
    public int char_count;
}
