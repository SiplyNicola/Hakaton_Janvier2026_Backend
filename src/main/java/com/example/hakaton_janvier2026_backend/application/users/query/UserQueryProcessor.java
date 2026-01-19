package com.example.hakaton_janvier2026_backend.application.users.query;

import com.example.hakaton_janvier2026_backend.application.users.query.getById.GetByIdUserHandler;
import org.springframework.stereotype.Service;

@Service
public class UserQueryProcessor {
    public final GetByIdUserHandler getByIdUserHandler;

    public UserQueryProcessor(GetByIdUserHandler getByIdUserHandler) {
        this.getByIdUserHandler = getByIdUserHandler;
    }
}
