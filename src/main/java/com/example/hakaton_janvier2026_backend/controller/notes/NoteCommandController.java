package com.example.hakaton_janvier2026_backend.controller.notes;

import com.example.hakaton_janvier2026_backend.application.notes.command.NoteCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteInput;
import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteOutput;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
