package com.example.hakaton_janvier2026_backend.application.folders.command.create;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateFolderOutput {
    private int id;
    private String name;
    private LocalDateTime createdAt;
    private int ownerId;
    private Integer parentId;
}