package com.example.hakaton_janvier2026_backend.application.notes.command;

import com.example.hakaton_janvier2026_backend.application.notes.command.create.CreateNoteHandler;
import com.example.hakaton_janvier2026_backend.application.notes.command.delete.DeleteNoteHandler;
import com.example.hakaton_janvier2026_backend.application.notes.command.switchmode.SwitchNoteModeHandler;
import com.example.hakaton_janvier2026_backend.application.notes.command.trashDeleteNote.TrashDeleteNoteHandler;
import com.example.hakaton_janvier2026_backend.application.notes.command.update.UpdateNoteHandler;
import org.springframework.stereotype.Service;

@Service
public class NoteCommandProcessor {

    public final CreateNoteHandler createNoteHandler;
    public final UpdateNoteHandler updateNoteHandler;
    public final DeleteNoteHandler deleteNoteHandler;
    public final SwitchNoteModeHandler switchNoteModeHandler;
    public final TrashDeleteNoteHandler trashDeleteNoteHandler;

    public NoteCommandProcessor(CreateNoteHandler createNoteHandler, UpdateNoteHandler updateNoteHandler, DeleteNoteHandler deleteNoteHandler, SwitchNoteModeHandler switchNoteModeHandler, TrashDeleteNoteHandler trashDeleteNoteHandler) {
        this.createNoteHandler = createNoteHandler;
        this.updateNoteHandler = updateNoteHandler;
        this.deleteNoteHandler = deleteNoteHandler;
        this.switchNoteModeHandler = switchNoteModeHandler;
        this.trashDeleteNoteHandler = trashDeleteNoteHandler;

    }
}
