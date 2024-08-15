package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.UserEntity;
import com.example.epulazproject.model.request.UserRequestDto;
import com.example.epulazproject.model.response.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserResponseDto mapToResponseDto(UserEntity userEntity);

    public abstract List<UserResponseDto> mapToDto(List<UserEntity> userEntity);
    public abstract UserEntity mapToEntity(UserRequestDto userRequestDto);
}
