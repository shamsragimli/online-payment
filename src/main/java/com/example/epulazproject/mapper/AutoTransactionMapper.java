package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.AutoTransactionEntity;
import com.example.epulazproject.model.request.AutoTransactionReq;
import com.example.epulazproject.model.response.AutoTransactionRes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AutoTransactionMapper {
    public abstract AutoTransactionEntity mapToEntity(AutoTransactionReq autoTransactionReq);
    public abstract AutoTransactionRes mapToDto(AutoTransactionEntity autoTransaction);

    public abstract List<AutoTransactionRes> mapToDto(List<AutoTransactionEntity> autoTransactionEntities);
}
