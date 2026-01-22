package com.example.hakaton_janvier2026_backend.application.folders.query.getAll;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GetFoldersOutput {
    private int id;
    private String name;
    private Integer parentId;

    private List<GetFoldersOutput> children;
}