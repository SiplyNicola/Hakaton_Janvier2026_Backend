package com.example.hakaton_janvier2026_backend.application.folders.command.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFolderInput {
    private int folderId;
}