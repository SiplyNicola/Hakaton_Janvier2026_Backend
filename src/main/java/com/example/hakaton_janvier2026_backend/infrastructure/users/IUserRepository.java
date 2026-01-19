package com.example.hakaton_janvier2026_backend.infrastructure.users;

import com.example.hakaton_janvier2026_backend.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<DbUser,Integer> {
    boolean existsByUsername(String username);
}
