package com.example.hakaton_janvier2026_backend.controller.notes;

import com.example.hakaton_janvier2026_backend.application.notes.command.NoteCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteOutput;
import com.example.hakaton_janvier2026_backend.application.notes.command.delete.DeleteNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.update.UpdateNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.update.UpdateNoteOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/note")
public class NoteCommandController {

    private final NoteCommandProcessor noteCommandProcessor;

    public NoteCommandController(NoteCommandProcessor noteCommandProcessor) {
        this.noteCommandProcessor = noteCommandProcessor;
    }

    @Operation(summary = "Create a note and assocate to a folder")
    @PostMapping()
    public ResponseEntity<CreateNoteOutput> addItem(@Valid @RequestBody CreateNoteInput createNoteInput) {
        CreateNoteOutput createNoteOutput = this.noteCommandProcessor.createNoteHandler.handle(createNoteInput);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createNoteOutput.id)
                .toUri();

        return ResponseEntity.created(location).body(createNoteOutput);
    }

    @Operation(summary = "Update a note (title/content/folder)")
    @PutMapping("/{id}")
    public ResponseEntity<UpdateNoteOutput> updateItem(
            @PathVariable int id,
            @Valid @RequestBody UpdateNoteInput updateNoteInput) {


        if (id != updateNoteInput.id) {
            return ResponseEntity.badRequest().build();
        }

        // Appel du handler via le processor pour traiter la logique métier et les métadonnées
        UpdateNoteOutput updateNoteOutput = this.noteCommandProcessor.updateNoteHandler.handle(updateNoteInput);

        // Retourne un code 200 OK avec l'objet contenant les métadonnées à jour
        return ResponseEntity.ok(updateNoteOutput);
    }

    @Operation(summary = "Delete a note")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable int id) {
        try {
            noteCommandProcessor.deleteNoteHandler.handle(DeleteNoteInput.builder().id(id).build());
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }


}
