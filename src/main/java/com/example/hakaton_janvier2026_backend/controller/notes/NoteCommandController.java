package com.example.hakaton_janvier2026_backend.controller.notes;

import com.example.hakaton_janvier2026_backend.application.notes.command.NoteCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteOutput;
import com.example.hakaton_janvier2026_backend.application.notes.command.update.UpdateNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.update.UpdateNoteOutput;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/note")
public class NoteCommandController {

    private final NoteCommandProcessor noteCommandProcessor;

    public NoteCommandController(NoteCommandProcessor noteCommandProcessor) {
        this.noteCommandProcessor = noteCommandProcessor;
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<UpdateNoteOutput> updateItem(
            @PathVariable int id,
            @Valid @RequestBody UpdateNoteInput updateNoteInput) {

        // Sécurité : on s'assure que l'ID de l'URL correspond à l'ID du corps de la requête
        if (id != updateNoteInput.id) {
            return ResponseEntity.badRequest().build();
        }

        // Appel du handler via le processor pour traiter la logique métier et les métadonnées
        UpdateNoteOutput updateNoteOutput = this.noteCommandProcessor.updateNoteHandler.handle(updateNoteInput);

        // Retourne un code 200 OK avec l'objet contenant les métadonnées à jour
        return ResponseEntity.ok(updateNoteOutput);
    }
}
