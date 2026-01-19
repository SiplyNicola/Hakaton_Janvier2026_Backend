package com.example.hakaton_janvier2026_backend.application.folders.query.getall;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class FolderTreeOutput {
    private int id;
    private String name;
    private Integer parentId;

    // La liste des sous-dossiers (enfants)
    private List<FolderTreeOutput> children;
}