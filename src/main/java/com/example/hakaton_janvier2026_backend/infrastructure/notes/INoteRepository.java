package com.example.hakaton_janvier2026_backend.infrastructure.notes;

import com.example.hakaton_janvier2026_backend.domain.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INoteRepository extends CrudRepository<DbNote,Integer> {

}
