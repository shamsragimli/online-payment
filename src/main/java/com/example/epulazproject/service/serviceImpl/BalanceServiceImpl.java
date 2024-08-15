package com.example.epulazproject.service.serviceImpl;

import com.example.epulazproject.exception.ValidationException;
import com.example.epulazproject.model.request.FromCashbackToBalanceDto;
import com.example.epulazproject.repository.BalanceRepository;
import com.example.epulazproject.repository.CardRepository;
import com.example.epulazproject.repository.UserRepository;
import com.example.epulazproject.exception.NotEnoughBalance;
import com.example.epulazproject.exception.NotFoundException;
import com.example.epulazproject.mapper.BalanceMapper;
import com.example.epulazproject.model.request.BalanceRequestDto;
import com.example.epulazproject.model.response.BalanceResponseDto;
import com.example.epulazproject.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService {
    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;



    public BalanceResponseDto transferFromCard(BalanceRequestDto balanceRequestDto) {
        log.info("ActionLog.transferFromCard.start: balanceRequestDto {}", balanceRequestDto);

        var userEntity = userRepository.findById(balanceRequestDto.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var cardEntity = cardRepository.findById(balanceRequestDto.getCardId()).orElseThrow(
                () -> new NotFoundException("CARD_NOT_FOUND")
        );
        var balanceEntity = balanceRepository.findByUserId(balanceRequestDto.getUserId());

        if (balanceRequestDto.getAmount() > cardEntity.getAmount()) {
            throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");
        }

        balanceEntity.setAmount(balanceEntity.getAmount() + balanceRequestDto.getAmount());
        cardEntity.setAmount(cardEntity.getAmount() - balanceRequestDto.getAmount());
        userEntity.setBalance(balanceEntity);
        userRepository.save(userEntity);
        cardRepository.save(cardEntity);
        balanceRepository.save(balanceEntity);
        var balanceResponseDto = balanceMapper.mapToDto(balanceEntity);
        log.info("ActionLog.transferFromCard.end: balanceResponseDto {}", balanceResponseDto);
        return balanceResponseDto;
    }


    public BalanceResponseDto transferToCard(BalanceRequestDto balanceRequestDto) {
        log.info("ActionLog.transferToCard.start: balanceRequestDto {}", balanceRequestDto);

        var cardEntity = cardRepository.findById(balanceRequestDto.getCardId()).orElseThrow(
                () -> new NotFoundException("CARD_NOT_FOUND")
        );
        var userEntity = userRepository.findById(balanceRequestDto.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var balanceEntity = balanceRepository.findByUserId(balanceRequestDto.getUserId());
        if (balanceRequestDto.getAmount() > balanceEntity.getAmount()) {
            throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");
        }
        balanceEntity.setAmount(balanceEntity.getAmount() - balanceRequestDto.getAmount());
        cardEntity.setAmount(cardEntity.getAmount() + balanceRequestDto.getAmount());
        userEntity.setBalance(balanceEntity);
        userRepository.save(userEntity);
        cardRepository.save(cardEntity);
        balanceRepository.save(balanceEntity);
        var balanceResponseDto = balanceMapper.mapToDto(balanceEntity);
        log.info("ActionLog.transferToCard.end: balanceResponseDto {}", balanceResponseDto);
        return balanceResponseDto;

    }

    public List<BalanceResponseDto> getAll(){
        var balanceEntityList = balanceRepository.findAll();
        var balanceResponseDtoList = balanceMapper.mapToDto(balanceEntityList);
        return balanceResponseDtoList;
    }

    public BalanceResponseDto get(Integer id){
        log.info("ActionLog.get.start: id {}", id);
        var balanceEntity = balanceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("BALANCE_NOT_FOUND")
        );
       var balanceResponseDto = balanceMapper.mapToDto(balanceEntity);
        log.info("ActionLog.get.end: balanceResponseDto {}", balanceResponseDto);
        return balanceResponseDto;
    }

    public BalanceResponseDto fromCashbackToCard(FromCashbackToBalanceDto fromCashbackToBalanceDto){
        log.info("ActionLog.fromCashbackToCard.start: fromCashbackToBalanceDto {}", fromCashbackToBalanceDto);
        var balanceEntity = balanceRepository.findById(fromCashbackToBalanceDto.getBalanceId()).orElseThrow(
                () ->  new NotFoundException("BALANCE_NOT_FOUND")
        );
        if (balanceEntity.getCashbackAmount() >= 5.0 && balanceEntity.getCashbackAmount() >= fromCashbackToBalanceDto.getTransferAmount()){
           var newBalance =  balanceEntity.getAmount() + fromCashbackToBalanceDto.getTransferAmount();
           var newCashbackBalance = balanceEntity.getCashbackAmount() - fromCashbackToBalanceDto.getTransferAmount();
           balanceEntity.setCashbackAmount(newCashbackBalance);
           balanceEntity.setAmount(newBalance);
           balanceRepository.save(balanceEntity);
        } else {
            throw new ValidationException("Cashback balance must be greater than 5.0 or amount less than the cashback balance.");
        }

        var balanceResponseDto = balanceMapper.mapToDto(balanceEntity);
        log.info("ActionLog.fromCashbackToCard.end: balanceResponseDto {}", balanceResponseDto);
        return balanceResponseDto;

    }


}
