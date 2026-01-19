package com.example.hakaton_janvier2026_backend.application.folders.query;

import com.example.hakaton_janvier2026_backend.application.folders.query.getall.FolderTreeOutput;
import com.example.hakaton_janvier2026_backend.application.folders.query.getall.GetFoldersHandler;
import com.example.hakaton_janvier2026_backend.application.folders.query.getall.GetFoldersInput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderQueryProcessor {

    private final GetFoldersHandler getFoldersHandler;

    public FolderQueryProcessor(GetFoldersHandler getFoldersHandler) {
        this.getFoldersHandler = getFoldersHandler;
    }

    public List<FolderTreeOutput> process(GetFoldersInput input) {
        return getFoldersHandler.handle(input);
    }
}