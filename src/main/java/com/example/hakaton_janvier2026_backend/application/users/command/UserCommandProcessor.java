package com.example.hakaton_janvier2026_backend.application.users.command;

import com.example.hakaton_janvier2026_backend.application.users.command.create.UserCreateHandle;
import com.example.hakaton_janvier2026_backend.application.users.command.login.UserLoginHandle;
import org.springframework.stereotype.Service;

@Service
public class UserCommandProcessor {
    public final UserCreateHandle userCreateHandle;
    public final UserLoginHandle userLoginHandle;

    public UserCommandProcessor(UserCreateHandle userCreateHandle,  UserLoginHandle userLoginHandle) {
        this.userCreateHandle = userCreateHandle;
        this.userLoginHandle = userLoginHandle;
    }
}
