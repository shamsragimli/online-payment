package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.SupportTicket;
import com.example.epulazproject.model.request.SupportRequestDto;
import com.example.epulazproject.model.response.SupportResponseDto;
import com.example.epulazproject.model.response.SupportUpdateDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SupportMapper {
    public abstract SupportResponseDto mapToDto(SupportTicket supportTicket);

    public abstract List<SupportResponseDto> mapToDto(List<SupportTicket> supportTicket);

    public abstract SupportTicket mapToEntity(SupportRequestDto supportRequestDto);
    public abstract SupportTicket mapToEntity(SupportUpdateDto supportUpdateDto);


}
