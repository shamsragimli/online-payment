package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.TransactionEntity;
import com.example.epulazproject.model.request.*;
import com.example.epulazproject.model.response.TransactionResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {
        public abstract TransactionEntity mapToEntity(TransactionCardDto transactionCardDto);
        public abstract TransactionEntity mapToEntity(TransactionBalanceDto transactionBalanceDto);
        public abstract TransactionEntity mapToEntity1(TransactionCardToCard transactionCardToCard);
        public abstract TransactionEntity mapToEntity(TransactionCardForFavPayment transactionCardForFavPayment);
        public abstract TransactionEntity mapToEntity(TransactionBalanceForFavPayment transactionBalanceForFavPayment);
        public abstract TransactionResponseDto mapToDto(TransactionEntity transactionEntity);
        public abstract List<TransactionResponseDto> mapToDto(List<TransactionEntity> transactionEntities);
}
