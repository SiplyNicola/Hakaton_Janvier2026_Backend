package com.example.hakaton_janvier2026_backend.application.folders.command.delete;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteFolderOutput {
    private int deletedFolderId;
    private boolean success;
}