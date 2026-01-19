package com.example.hakaton_janvier2026_backend.application.users.query.getById;

import com.example.hakaton_janvier2026_backend.application.exceptions.UserNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.IQueryHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GetByIdUserHandler implements IQueryHandler<Integer, GetByIdUserOutput> {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public GetByIdUserHandler(IUserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GetByIdUserOutput handle(Integer input) {
        return userRepository.findById(input)
                .map(user -> modelMapper.map(user, GetByIdUserOutput.class))
                .orElseThrow(() -> new UserNotFoundException());
    }
}
