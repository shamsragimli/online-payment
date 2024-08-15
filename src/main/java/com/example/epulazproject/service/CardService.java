package com.example.epulazproject.service;

import com.example.epulazproject.model.request.BalanceRequestDto;
import com.example.epulazproject.model.request.CardRequestDto;
import com.example.epulazproject.model.response.BalanceResponseDto;
import com.example.epulazproject.model.response.CardResponseDto;

import java.util.List;

public interface CardService {
     List<CardResponseDto> getAll();
    void create(CardRequestDto cardRequestDto);
     void update(Integer id, CardRequestDto cardRequestDto);
     void delete(Integer id);
     CardResponseDto get(Integer cardId);


    }
