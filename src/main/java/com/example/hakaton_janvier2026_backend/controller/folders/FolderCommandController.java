package com.example.hakaton_janvier2026_backend.controller.folders;

import com.example.hakaton_janvier2026_backend.application.folders.command.FolderCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderOutput;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderOutput;
import com.example.hakaton_janvier2026_backend.application.folders.command.update.UpdateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.update.UpdateFolderOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create a folder")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Folder is created !", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "User or parent folder is not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @PostMapping
    public ResponseEntity<CreateFolderOutput> createFolder(@RequestBody CreateFolderInput input) {
        CreateFolderOutput output = folderCommandProcessor.process(input);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    // DELETE (Supprimer)
    @Operation(summary = "Delete a folder")
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteFolderOutput> deleteFolder(@PathVariable int id) {
        DeleteFolderInput input = new DeleteFolderInput(id);
        DeleteFolderOutput output = folderCommandProcessor.process(input);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    // PUT (Update: Renommer / Déplacer)
    @Operation(summary = "Update a folder (rename/move)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Folder updated !",  content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Folder not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "A folder can not be his own parent folder !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UpdateFolderOutput> updateFolder(@PathVariable int id, @RequestBody UpdateFolderInput input) {
        // On s'assure que l'ID du path est bien dans l'input
        input.setId(id);
        UpdateFolderOutput output = folderCommandProcessor.process(input);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}