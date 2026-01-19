package com.example.hakaton_janvier2026_backend.controller.folders;

import com.example.hakaton_janvier2026_backend.application.folders.command.FolderCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/folders")
public class FolderCommandController {

    private final FolderCommandProcessor folderCommandProcessor;

    public FolderCommandController(FolderCommandProcessor folderCommandProcessor) {
        this.folderCommandProcessor = folderCommandProcessor;
    }

    @PostMapping
    public ResponseEntity<CreateFolderOutput> createFolder(@RequestBody CreateFolderInput input) {
        // Le controller délègue tout au Processor
        CreateFolderOutput output = folderCommandProcessor.process(input);

        // Retourne 201 Created avec la réponse
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }
}