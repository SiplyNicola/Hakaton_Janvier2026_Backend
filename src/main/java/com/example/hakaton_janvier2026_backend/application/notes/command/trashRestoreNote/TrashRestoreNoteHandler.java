package com.example.hakaton_janvier2026_backend.application.notes.command.trashRestoreNote;

import com.example.hakaton_janvier2026_backend.application.exceptions.NoteNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TrashRestoreNoteHandler implements ICommandHandler<Integer, TrashRestoreNoteOutput> {
    private final INoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public TrashRestoreNoteHandler(INoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public TrashRestoreNoteOutput handle(Integer input) {
        DbNote note = noteRepository.findById(input).orElseThrow(NoteNotFoundException::new);

        note.deletedAt = null;

        noteRepository.save(note);

        return modelMapper.map(note, TrashRestoreNoteOutput.class);
    }
}
