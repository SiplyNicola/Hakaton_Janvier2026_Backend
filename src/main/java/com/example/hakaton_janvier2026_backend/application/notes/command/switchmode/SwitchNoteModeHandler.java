
package com.example.hakaton_janvier2026_backend.application.notes.command.switchmode;

import com.example.hakaton_janvier2026_backend.application.exceptions.NoteNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SwitchNoteModeHandler implements ICommandHandler<SwitchNoteModeInput, SwitchNoteModeOutput> {

    private final INoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public SwitchNoteModeHandler(INoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SwitchNoteModeOutput handle(SwitchNoteModeInput input) {
        // Retrieval
        DbNote note = noteRepository.findById(input.id)
                .orElseThrow(NoteNotFoundException::new);

        // Update
        note.isWriteMode = input.is_write_mode;

        // Persistence
        DbNote savedNote = noteRepository.save(note);

        // Mapping (manual or via ModelMapper)
        // Manual mapping to ensure field names, or modelMapper.map(savedNote, SwitchNoteModeOutput.class)
        SwitchNoteModeOutput output = new SwitchNoteModeOutput();
        output.id = savedNote.id;
        output.is_write_mode = savedNote.isWriteMode;

        return output;
    }
}
