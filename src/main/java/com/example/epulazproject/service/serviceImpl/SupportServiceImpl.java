package com.example.epulazproject.service.serviceImpl;

import com.example.epulazproject.enums.SupportStatus;
import com.example.epulazproject.exception.NotFoundException;
import com.example.epulazproject.mapper.SupportMapper;
import com.example.epulazproject.model.request.SupportRequestDto;
import com.example.epulazproject.model.response.SupportResponseDto;
import com.example.epulazproject.model.response.SupportUpdateDto;
import com.example.epulazproject.repository.SupportRepository;
import com.example.epulazproject.service.SupportTicket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupportServiceImpl implements SupportTicket {
    private final SupportRepository supportRepository;
    private final SupportMapper supportMapper;

    public SupportResponseDto create(SupportRequestDto supportRequestDto){
        log.info("ActionLog.create.start: supportRequestDto {}", supportRequestDto);
        var supportEntity = supportMapper.mapToEntity(supportRequestDto);
        supportEntity.setStatus(SupportStatus.OPEN);
        supportRepository.save(supportEntity);
        var supportResponseDto = supportMapper.mapToDto(supportEntity);
        log.info("ActionLog.create.end: supportResponseDto {}", supportResponseDto);
        return supportResponseDto;
    }

    public SupportResponseDto update(Integer id, SupportUpdateDto supportUpdateDto){
        log.info("ActionLog.update.start: id, supportRequestDto {} {}", id, supportUpdateDto);
        var supportEntity = supportRepository.findById(id).orElseThrow(
                () -> new NotFoundException("SUPPORT_NOT_FOUND")
        );
        supportEntity.setFeedback(supportUpdateDto.getResponse());
        supportEntity.setStatus(SupportStatus.CLOSE);
        supportEntity.setId(id);
        supportRepository.save(supportEntity);
        var supportResponseDto = supportMapper.mapToDto(supportEntity);
        log.info("ActionLog.update.end: supportResponseDto {}", supportResponseDto);
        return supportResponseDto;
    }

    public SupportResponseDto getById(Integer id){
        log.info("ActionLog.getById.start: id {}", id);
        var supportEntity = supportRepository.findById(id).orElseThrow(
                () -> new NotFoundException("SUPPORT_NOT_FOUND")
        );
        var supportResponseDto = supportMapper.mapToDto(supportEntity);
        log.info("ActionLog.getById.end: supportResponseDto {}", supportResponseDto);
        return supportResponseDto;
    }

    public List<SupportResponseDto> getAll(){
        var supportEntityList = supportRepository.findAll();
       var supportResponseDtoList = supportMapper.mapToDto(supportEntityList);
       return supportResponseDtoList;
    }

    public void delete(Integer id){
        log.info("ActionLog.delete.start: id {}", id);
        supportRepository.findById(id).orElseThrow(
                () -> new NotFoundException("SUPPORT_NOT_FOUND")
        );
        supportRepository.deleteById(id);
    }

    public List<SupportResponseDto> getFiltered(SupportStatus supportStatus){
        log.info("ActionLog.getFiltered.start: supportStatus {}", supportStatus);
        var supportTicketList = supportRepository.findByStatus(supportStatus);
      var supportResponseDtoList = supportMapper.mapToDto(supportTicketList);
        log.info("ActionLog.getFiltered.end: supportResponseDtoList {}", supportResponseDtoList);
       return supportResponseDtoList;
    }

}
