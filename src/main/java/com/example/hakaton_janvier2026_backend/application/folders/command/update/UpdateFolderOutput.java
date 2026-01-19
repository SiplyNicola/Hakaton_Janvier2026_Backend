package com.example.hakaton_janvier2026_backend.application.folders.command.update;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateFolderOutput {
    private int id;
    private String name;
    private Integer parentId;
    private int ownerId;
}