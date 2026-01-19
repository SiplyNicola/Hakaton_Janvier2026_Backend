package com.example.hakaton_janvier2026_backend.application.users.command;

import com.example.hakaton_janvier2026_backend.application.users.command.create.UserCreateHandle;
import org.springframework.stereotype.Service;

@Service
public class UserCommandProcessor {
    public final UserCreateHandle userCreateHandle;

    public UserCommandProcessor(UserCreateHandle userCreateHandle) {
        this.userCreateHandle = userCreateHandle;
    }
}
