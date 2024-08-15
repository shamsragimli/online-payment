package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.BalanceEntity;
import com.example.epulazproject.model.request.BalanceRequestDto;
import com.example.epulazproject.model.response.BalanceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BalanceMapper {
    public abstract BalanceEntity mapToEntity(BalanceRequestDto balanceRequestDto);
    public abstract BalanceResponseDto mapToDto(BalanceEntity balanceEntity);
    public abstract List<BalanceResponseDto> mapToDto(List<BalanceEntity> balanceEntityList);

}
