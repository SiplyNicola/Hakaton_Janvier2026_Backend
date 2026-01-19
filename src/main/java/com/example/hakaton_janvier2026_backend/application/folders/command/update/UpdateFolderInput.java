package com.example.hakaton_janvier2026_backend.application.folders.command.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFolderInput {
    private int id;           // L'ID du dossier à modifier
    private String name;      // Le nouveau nom
    private Integer parentId; // Le nouveau parent (null = racine)
}