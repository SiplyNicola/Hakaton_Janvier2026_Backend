package com.example.hakaton_janvier2026_backend.application.notes.command.trashDeleteNote;

import com.example.hakaton_janvier2026_backend.application.exceptions.NoteNotFoundException;
import com.example.hakaton_janvier2026_backend.application.notes.command.delete.DeleteNoteHandler;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.users.DbUser;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrashDeleteNoteHandler implements ICommandHandler<Integer, TrashDeleteNoteOutput>{

    private final INoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public TrashDeleteNoteHandler(INoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public TrashDeleteNoteOutput handle(Integer input) {
        DbNote note = noteRepository.findById(input).orElseThrow(NoteNotFoundException::new);

        note.deletedAt = LocalDateTime.now();

        noteRepository.save(note);

        return modelMapper.map(note, TrashDeleteNoteOutput.class);
    }
}
