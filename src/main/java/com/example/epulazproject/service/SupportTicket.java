package com.example.epulazproject.service;

import com.example.epulazproject.enums.SupportStatus;
import com.example.epulazproject.model.request.SupportRequestDto;
import com.example.epulazproject.model.response.SupportResponseDto;
import com.example.epulazproject.model.response.SupportUpdateDto;

import java.util.List;

public interface SupportTicket {
     SupportResponseDto create(SupportRequestDto supportRequestDto);
     SupportResponseDto update(Integer id, SupportUpdateDto supportUpdateDto);
    SupportResponseDto getById(Integer id);
     List<SupportResponseDto> getAll();
     void delete(Integer id);
     List<SupportResponseDto> getFiltered(SupportStatus supportStatus);
}
