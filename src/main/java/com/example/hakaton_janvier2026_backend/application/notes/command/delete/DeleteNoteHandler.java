package com.example.hakaton_janvier2026_backend.application.notes.command.delete;

import com.example.hakaton_janvier2026_backend.application.exceptions.NoteNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandEmptyResponseHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;

@Service
public class DeleteNoteHandler implements ICommandEmptyResponseHandler<DeleteNoteInput> {

    private final INoteRepository noteRepository;

    public DeleteNoteHandler(INoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void handle(DeleteNoteInput input) {
        if(!noteRepository.existsById(input.id)){
            throw new NoteNotFoundException();
        }

        noteRepository.deleteById(input.id);
    }
}
