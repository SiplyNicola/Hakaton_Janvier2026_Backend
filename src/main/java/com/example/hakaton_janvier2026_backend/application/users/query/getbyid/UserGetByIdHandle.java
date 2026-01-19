package com.example.hakaton_janvier2026_backend.application.users.query.getbyid;

import com.example.hakaton_janvier2026_backend.application.exceptions.UserNotFoundException;
import com.example.hakaton_janvier2026_backend.application.users.command.login.UserLoginOutput;
import com.example.hakaton_janvier2026_backend.application.utils.IQueryHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.users.DbUser;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserGetByIdHandle implements IQueryHandler<Integer, UserGetByIdOutput> {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserGetByIdHandle(IUserRepository userRepository,  ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserGetByIdOutput handle(Integer input) {
        return userRepository.findById(input)
                .map(user -> modelMapper.map(user, UserGetByIdOutput.class))
                .orElseThrow(() -> new UserNotFoundException());
    }
}
