package com.example.epulazproject.service;

import com.example.epulazproject.enums.TransactionType;
import com.example.epulazproject.model.request.*;
import com.example.epulazproject.model.response.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto createTransactionWithCard(TransactionCardDto transactionCardDto);

    TransactionResponseDto createTransactionWithBalance(TransactionBalanceDto transactionBalanceDto);

    TransactionResponseDto createTransactionWithCardForFavPayment(TransactionCardForFavPayment transactionCardForFavPayment);

    TransactionResponseDto createTransactionWithBalanceForFavPayment(TransactionBalanceForFavPayment transactionBalanceForFavPayment);

    TransactionResponseDto createTransactionCardToCard(TransactionCardToCard transactionCardToCard);

    TransactionResponseDto getById(Integer id);

    List<TransactionResponseDto> getByType(TransactionType transactionType);

    List<TransactionResponseDto> getAll();

}
