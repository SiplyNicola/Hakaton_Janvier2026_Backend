package com.example.hakaton_janvier2026_backend.infrastructure.folders;

import com.example.hakaton_janvier2026_backend.domain.Folder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFolderRepository extends CrudRepository<DbFolder,Integer> {

}
