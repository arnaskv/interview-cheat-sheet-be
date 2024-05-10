package com.interview.manager.backend.services.auth;

import com.interview.manager.backend.dto.UserResponseDto;
import com.interview.manager.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private static final UserMapper Mapper = UserMapper.INSTANCE;

    public Optional<UserResponseDto> getById(UUID id) {
        return userRepository.findById(id)
            .map(Mapper::userResponseDto);
    }
}
