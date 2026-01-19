package com.example.hakaton_janvier2026_backend.application.users.command.login;

import com.example.hakaton_janvier2026_backend.application.exceptions.InvalidPasswordException;
import com.example.hakaton_janvier2026_backend.application.exceptions.UserNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.users.DbUser;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginHandle implements ICommandHandler<UserLoginInput, UserLoginOutput> {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserLoginHandle(IUserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserLoginOutput handle(UserLoginInput input) {
        UserLoginOutput output = new UserLoginOutput();

        //Verify if the username is existing in the database
        if(!userRepository.existsByUsername(input.username)) throw new UserNotFoundException();
        DbUser user = userRepository.getByUsername(input.username);

        //Verify if the password hash is the same in the database
        if(!passwordEncoder.matches(input.password, user.password)) throw new InvalidPasswordException();

        return modelMapper.map(user, UserLoginOutput.class);
    }
}
