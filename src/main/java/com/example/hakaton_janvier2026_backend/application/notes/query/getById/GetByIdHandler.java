package com.example.hakaton_janvier2026_backend.application.notes.query.getById;

import com.example.hakaton_janvier2026_backend.application.exceptions.NoteNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.IQueryHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GetByIdHandler implements IQueryHandler<Integer, GetByIdOutput> {
    private final INoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public GetByIdHandler(INoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GetByIdOutput handle(Integer input) {
        if(!noteRepository.existsById(input)) throw new NoteNotFoundException();
        DbNote note = noteRepository.findById(input).orElse(null);

        GetByIdOutput result = modelMapper.map(note, GetByIdOutput.class);
        result.owner_id = note.owner.id;
        if(note.folder != null)
            result.folder_id = note.folder.id;

        return result;
    }
}
