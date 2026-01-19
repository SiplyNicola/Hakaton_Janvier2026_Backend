package com.example.hakaton_janvier2026_backend.application.notes.command;

import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteHandler;
import com.example.hakaton_janvier2026_backend.application.notes.command.delete.DeleteNoteHandler;
import com.example.hakaton_janvier2026_backend.application.notes.command.update.UpdateNoteHandler;
import org.springframework.stereotype.Service;

@Service
public class NoteCommandProcessor {

    public final CreateNoteHandler createNoteHandler;
    public final UpdateNoteHandler updateNoteHandler;
    public final DeleteNoteHandler deleteNoteHandler;

    public NoteCommandProcessor(CreateNoteHandler createNoteHandler, UpdateNoteHandler updateNoteHandler, DeleteNoteHandler deleteNoteHandler) {
        this.createNoteHandler = createNoteHandler;
        this.updateNoteHandler = updateNoteHandler;
        this.deleteNoteHandler = deleteNoteHandler;
    }
}
