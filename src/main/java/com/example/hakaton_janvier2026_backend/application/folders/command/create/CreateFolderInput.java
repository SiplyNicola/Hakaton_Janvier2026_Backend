package com.example.hakaton_janvier2026_backend.application.folders.command.create;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateFolderInput {
    private String name;
    private int ownerId;
    private Integer parentId; // Optionnel (null = racine)
}