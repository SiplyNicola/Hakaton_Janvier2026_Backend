package com.example.hakaton_janvier2026_backend.application.users.command;

import com.example.hakaton_janvier2026_backend.application.users.command.create.CreateUserHandler;
import com.example.hakaton_janvier2026_backend.application.users.command.login.LoginUserHandler;
import org.springframework.stereotype.Service;

@Service
public class UserCommandProcessor {
    public final CreateUserHandler createUserHandler;
    public final LoginUserHandler loginUserHandler;

    public UserCommandProcessor(CreateUserHandler createUserHandler, LoginUserHandler loginUserHandler) {
        this.createUserHandler = createUserHandler;
        this.loginUserHandler = loginUserHandler;
    }
}
