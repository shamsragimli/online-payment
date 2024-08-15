package com.example.epulazproject.service.serviceImpl;

import com.example.epulazproject.repository.CardRepository;
import com.example.epulazproject.repository.UserRepository;
import com.example.epulazproject.exception.NotFoundException;
import com.example.epulazproject.mapper.CardMapper;
import com.example.epulazproject.model.request.CardRequestDto;
import com.example.epulazproject.model.response.CardResponseDto;
import com.example.epulazproject.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
        private final CardRepository cardRepository;
        private final CardMapper cardMapper;
        private final UserRepository userRepository;

    public List<CardResponseDto> getAll(){
        var cardEntityList = cardRepository.findAll();
        return cardMapper.mapToResponseDto(cardEntityList);
    }
        public CardResponseDto get(Integer id){
            log.info("ActionLog.get.start: id {}", id);
            var cardEntity = cardRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("CARD_NOT_FOUND")
            );
            log.info("ActionLog.get.end: cardId {}", id);
            return cardMapper.mapToResponseDto(cardEntity);
        }


        public void create(CardRequestDto cardRequestDto){
            log.info("ActionLog.create.start: cardRequestDto {}", cardRequestDto);
           var cardEntity = cardMapper.mapToEntity(cardRequestDto);
           var userEntity = userRepository.findById(cardRequestDto.getUserId()).orElseThrow(
                   () -> new NotFoundException("USER_NOT_FOUND")
           );
           cardEntity.setUser(userEntity);
           cardRepository.save(cardEntity);
           userEntity.setId(cardEntity.getId());
           userRepository.save(userEntity);
        }

    public void update(Integer id, CardRequestDto cardRequestDto){
        log.info("ActionLog.update.start: id {}, cardRequestDto {}", id, cardRequestDto);
        cardRepository.findById(id).orElseThrow(
                () -> new NotFoundException("CARD_NOT_FOUND")
        );
        var cardEntity = cardMapper.mapToEntity(cardRequestDto);
        cardEntity.setId(id);
        cardRepository.save(cardEntity);
    }

    public void delete(Integer id) {
        log.info("ActionLog.delete.start: id {}", id);
        cardRepository.findById(id).orElseThrow(
                () -> new NotFoundException("CARD_NOT_FOUND")
        );
        cardRepository.deleteById(id);
    }
}

