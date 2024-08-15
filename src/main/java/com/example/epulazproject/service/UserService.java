package com.example.epulazproject.service;

import com.example.epulazproject.model.request.UserPassword;
import com.example.epulazproject.model.request.UserRequestDto;
import com.example.epulazproject.model.response.*;

import java.util.List;

public interface UserService {
    UserResponseDto get(Integer id);
     List<UserResponseDto> getAll();
    UserResponseDto update(Integer id, UserRequestDto userRequestDto);
    UserResponseDto changePassword(Integer id, UserPassword userPassword);
    void delete(Integer id);
    List<CardResponseDto> getCards(Integer id);
    List<TransactionResponseDto> getTransactions(Integer id);
    BalanceResponseDto getBalance(Integer id);
    List<FavoritePaymentResponse> getFavPayment(Integer id);
    void createBalance(Integer id);


}
