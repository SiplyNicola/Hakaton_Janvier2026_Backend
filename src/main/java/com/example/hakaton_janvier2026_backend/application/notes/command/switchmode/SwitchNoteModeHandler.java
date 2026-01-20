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
        // 1. Récupération
        DbNote note = noteRepository.findById(input.id)
                .orElseThrow(NoteNotFoundException::new);

        // 2. Modification
        note.isWriteMode = input.is_write_mode;

        // 3. Sauvegarde
        DbNote savedNote = noteRepository.save(note);

        // 4. Mapping (Manuel ou via ModelMapper)
        // Ici manuel pour être sûr des noms de champs, ou modelMapper.map(savedNote, SwitchNoteModeOutput.class)
        SwitchNoteModeOutput output = new SwitchNoteModeOutput();
        output.id = savedNote.id;
        output.is_write_mode = savedNote.isWriteMode;

        return output;
    }
}