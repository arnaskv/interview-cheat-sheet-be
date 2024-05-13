package com.interview.manager.backend.services.user;

import com.interview.manager.backend.dto.UserResponseDto;
import com.interview.manager.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDto userResponseDto(User user);
}
