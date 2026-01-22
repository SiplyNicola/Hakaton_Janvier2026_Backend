package com.example.hakaton_janvier2026_backend.controller.notes;

import com.example.hakaton_janvier2026_backend.application.notes.command.NoteCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteOutput;
import com.example.hakaton_janvier2026_backend.application.notes.command.delete.DeleteNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.switchmode.SwitchNoteModeInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.switchmode.SwitchNoteModeOutput;
import com.example.hakaton_janvier2026_backend.application.notes.command.trashDeleteNote.TrashDeleteNoteOutput;
import com.example.hakaton_janvier2026_backend.application.notes.command.trashRestoreNote.TrashRestoreNoteOutput;
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

    // Create a note
    @Operation(summary = "Create a note and assocate to a folder")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Note is created !", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Folders or User is not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
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

    // Update a note (title, content, or his folder parent)
    @Operation(summary = "Update a note (title/content/folder)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note updated !", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Note or parent folder not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UpdateNoteOutput> updateItem(
            @PathVariable int id,
            @Valid @RequestBody UpdateNoteInput updateNoteInput) {

        if (id != updateNoteInput.id) {
            return ResponseEntity.badRequest().build();
        }

        UpdateNoteOutput updateNoteOutput = this.noteCommandProcessor.updateNoteHandler.handle(updateNoteInput);
        return ResponseEntity.ok(updateNoteOutput);
    }

    // Delete a note
    @Operation(summary = "Delete a note")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Note deleted !", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Note is not found !",
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

    // Change the note mode
    @Operation(summary = "Change note mode (Read/Write)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note mode updated !", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Note is not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @PatchMapping("/{id}/mode")
    public ResponseEntity<SwitchNoteModeOutput> switchMode(@PathVariable int id, @RequestBody SwitchNoteModeInput input) {
        input.id = id;

        SwitchNoteModeOutput output = noteCommandProcessor.switchNoteModeHandler.handle(input);
        return ResponseEntity.ok(output);
    }

    // Move the note into the bin
    @Operation(summary = "Update deleledAt")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Folder updated !",  content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Folder not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @PutMapping("/trash/{id}")
    public ResponseEntity<TrashDeleteNoteOutput> updateFolder(@PathVariable int id) {

       TrashDeleteNoteOutput output = noteCommandProcessor.trashDeleteNoteHandler.handle(id);
        return new ResponseEntity<>(output, HttpStatus.OK);

    }

    // Move the note from the bin into the active folders
    @Operation(summary = "Restore a note")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Folder restored !",  content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Folder not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @PutMapping("/restore/{id}")
    public ResponseEntity<TrashRestoreNoteOutput> restoreFolder(@PathVariable int id) {
        TrashRestoreNoteOutput output = noteCommandProcessor.trashRestoreNoteHandler.handle(id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }


}
