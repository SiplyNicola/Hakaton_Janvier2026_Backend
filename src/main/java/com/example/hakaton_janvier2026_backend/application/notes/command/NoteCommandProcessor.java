package com.example.hakaton_janvier2026_backend.application.notes.command;

import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteHandler;
import org.springframework.stereotype.Service;

@Service
public class NoteCommandProcessor {

    public final CreateNoteHandler createNoteHandler;

    public NoteCommandProcessor(CreateNoteHandler createNoteHandler) {
        this.createNoteHandler = createNoteHandler;
    }
}
