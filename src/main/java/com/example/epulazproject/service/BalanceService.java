package com.example.epulazproject.service;

import com.example.epulazproject.model.request.BalanceRequestDto;
import com.example.epulazproject.model.request.FromCashbackToBalanceDto;
import com.example.epulazproject.model.response.BalanceResponseDto;

import java.util.List;

public interface BalanceService {
    BalanceResponseDto transferFromCard(BalanceRequestDto balanceRequestDto);
     BalanceResponseDto transferToCard(BalanceRequestDto balanceRequestDto);
     List<BalanceResponseDto> getAll();
     BalanceResponseDto get(Integer id);

     BalanceResponseDto fromCashbackToCard(FromCashbackToBalanceDto fromCashbackToBalanceDto);




    }
