package com.example.hakaton_janvier2026_backend.controller.folders;

import com.example.hakaton_janvier2026_backend.application.folders.query.FolderQueryProcessor;
import com.example.hakaton_janvier2026_backend.application.folders.query.getall.FolderTreeOutput;
import com.example.hakaton_janvier2026_backend.application.folders.query.getall.GetFoldersInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class FolderQueryController {

    private final FolderQueryProcessor folderQueryProcessor;

    public FolderQueryController(FolderQueryProcessor folderQueryProcessor) {
        this.folderQueryProcessor = folderQueryProcessor;
    }

    // GET http://localhost:8080/api/folders?ownerId=1
    @GetMapping
    public ResponseEntity<List<FolderTreeOutput>> getAllFolders(@RequestParam int ownerId) {
        GetFoldersInput input = new GetFoldersInput(ownerId);
        List<FolderTreeOutput> tree = folderQueryProcessor.process(input);
        return ResponseEntity.ok(tree);
    }
}