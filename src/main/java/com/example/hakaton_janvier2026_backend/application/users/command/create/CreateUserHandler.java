package com.example.hakaton_janvier2026_backend.application.users.command.create;

import com.example.hakaton_janvier2026_backend.application.exceptions.UserAlreadyExistsException;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.users.DbUser;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserHandler implements ICommandHandler<CreateUserInput, CreateUserOutput> {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public CreateUserHandler(IUserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserOutput handle(CreateUserInput input) {
        CreateUserOutput output = new CreateUserOutput();

        // Verify if the username is already used in the database
        if(userRepository.existsByUsername(input.username)) throw new UserAlreadyExistsException(input.username);

        input.password = passwordEncoder.encode(input.password);
        DbUser user = modelMapper.map(input, DbUser.class);
        DbUser result = userRepository.save(user);

        modelMapper.map(result, output);

        return output;
    }
}
