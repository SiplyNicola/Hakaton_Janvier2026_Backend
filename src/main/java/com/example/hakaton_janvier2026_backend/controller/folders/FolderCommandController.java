package com.example.hakaton_janvier2026_backend.controller.folders;

import com.example.hakaton_janvier2026_backend.application.folders.command.FolderCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderOutput;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
public class FolderCommandController {

    private final FolderCommandProcessor folderCommandProcessor;

    public FolderCommandController(FolderCommandProcessor folderCommandProcessor) {
        this.folderCommandProcessor = folderCommandProcessor;
    }

    // POST (Créer)
    @PostMapping
    public ResponseEntity<CreateFolderOutput> createFolder(@RequestBody CreateFolderInput input) {
        CreateFolderOutput output = folderCommandProcessor.process(input);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    // DELETE (Supprimer)
    // URL: DELETE http://localhost:8080/api/folders/12
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteFolderOutput> deleteFolder(@PathVariable int id) {
        DeleteFolderInput input = new DeleteFolderInput(id);
        DeleteFolderOutput output = folderCommandProcessor.process(input);

        // On renvoie 200 OK avec le corps de réponse
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}