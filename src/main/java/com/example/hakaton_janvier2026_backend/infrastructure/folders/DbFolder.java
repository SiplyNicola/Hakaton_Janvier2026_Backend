package com.example.hakaton_janvier2026_backend.infrastructure.folders;

import com.example.hakaton_janvier2026_backend.infrastructure.users.DbUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "folders")
public class DbFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    //Data
    public String name;
    public LocalDateTime created_at;
    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;

    // Foreign key: owner_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    public DbUser owner;

    // Foreign Key: parent_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    public DbFolder parentFolder;
}
