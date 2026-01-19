package com.example.hakaton_janvier2026_backend.application.users.query;

import com.example.hakaton_janvier2026_backend.application.users.command.login.UserLoginHandle;
import com.example.hakaton_janvier2026_backend.application.users.query.getbyid.UserGetByIdHandle;
import org.springframework.stereotype.Service;

@Service
public class UserQueryProcessor {
    public final UserGetByIdHandle userGetByIdHandle;

    public UserQueryProcessor(UserGetByIdHandle userGetByIdHandle) {
        this.userGetByIdHandle = userGetByIdHandle;
    }
}
