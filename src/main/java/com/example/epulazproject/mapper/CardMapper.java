package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.CardEntity;
import com.example.epulazproject.model.request.CardRequestDto;
import com.example.epulazproject.model.response.CardResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CardMapper {
    public abstract CardResponseDto mapToResponseDto(CardEntity cardEntity);
    public abstract CardRequestDto mapToRequestDto(CardEntity cardEntity);
    public abstract CardEntity mapToEntity(CardRequestDto cardRequestDto);

    public abstract List<CardResponseDto> mapToResponseDto(List<CardEntity> cardEntity);
}
