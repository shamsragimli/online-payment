package com.example.epulazproject.service.serviceImpl;

import com.example.epulazproject.dao.BalanceEntity;
import com.example.epulazproject.dao.FavoritePaymentEntity;
import com.example.epulazproject.mapper.*;
import com.example.epulazproject.model.request.UserPassword;
import com.example.epulazproject.model.request.UserRequestDto;
import com.example.epulazproject.model.response.*;
import com.example.epulazproject.repository.BalanceRepository;
import com.example.epulazproject.repository.FavoritePaymentRepository;
import com.example.epulazproject.repository.UserRepository;
import com.example.epulazproject.exception.NotFoundException;

import com.example.epulazproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;
    private final FavoritePaymentRepository favoritePaymentRepository;
    private final FavoritePaymentMapper favoritePaymentMapper;
    private final PaymentMapper paymentMapper;
    private final FieldMapper fieldMapper;

    @Transactional
    public UserResponseDto get(Integer id) {
        log.info("ActionLog.get.start: id {}", id);
        var userEntity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var userResponseDto = userMapper.mapToResponseDto(userEntity);
        log.info("ActionLog.get.end: userResponseDto {}", userResponseDto);
        return userResponseDto;
    }

    @Transactional
    public List<UserResponseDto> getAll() {
        var userEntityList = userRepository.findAll();
        return userMapper.mapToDto(userEntityList);
    }

    public UserResponseDto update(Integer id, UserRequestDto userRequestDto) {
        log.info("ActionLog.update.start: id {} userRequestDto {}", id, userRequestDto);
        var userEntity = userMapper.mapToEntity(userRequestDto);
        userEntity.setId(id);
        userRepository.save(userEntity);
        var userResponseDto = userMapper.mapToResponseDto(userEntity);
        log.info("ActionLog.update.end: userResponseDto {}", userResponseDto);
        return userResponseDto;
    }

    @Transactional
    public UserResponseDto changePassword(Integer id, UserPassword userPassword) {
        log.info("ActionLog.changePassword.start: id {} userPassword {}", id, userPassword);
        var userEntity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        userEntity.setId(id);
        userEntity.setPassword(userPassword.getPassword());
        var userResponseDto = userMapper.mapToResponseDto(userEntity);
        log.info("ActionLog.changePassword.end: userResponseDto {}", userResponseDto);
        return userResponseDto;
    }

    public void delete(Integer id) {
        log.info("ActionLog.delete.start: id {}", id);
        userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        userRepository.deleteById(id);
    }

    @Transactional
    public List<CardResponseDto> getCards(Integer id) {
        log.info("ActionLog.getCard.start: id {}", id);
        var userEntity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var cardResponseDtoList = cardMapper.mapToResponseDto(userEntity.getCards());
        log.info("ActionLog.getCard.end: cardResponseDtoList {}", cardResponseDtoList);
        return cardResponseDtoList;
    }

    @Transactional
    public List<TransactionResponseDto> getTransactions(Integer id) {
        log.info("ActionLog.getTransaction.start: id {}", id);
        var userEntity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var transactionResponseDtoList = transactionMapper.mapToDto(userEntity.getTransactions());
        log.info("ActionLog.getTransaction.end: transactionResponseDtoList {}", transactionResponseDtoList);
        return transactionResponseDtoList;
    }

    public BalanceResponseDto getBalance(Integer id) {
        log.info("ActionLog.getBalance.start: id {}", id);

        userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var balanceEntity = balanceRepository.findByUserId(id);
        var balanceResponseDto = balanceMapper.mapToDto(balanceEntity);
        log.info("ActionLog.getBalance.end: balanceResponseDto {}", balanceResponseDto);
        return balanceResponseDto;
    }
    @Transactional
    public List<FavoritePaymentResponse> getFavPayment(Integer id){
        log.info("ActionLog.getFavPayment.start: id {}", id);
        userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var favoritePaymentEntityList = favoritePaymentRepository.findByUserId(id);
        for (FavoritePaymentEntity favoritePaymentEntity : favoritePaymentEntityList) {
            var paymentEntity = favoritePaymentEntity.getPayment();
            var fieldEntity = favoritePaymentEntity.getField();
            var favoritePaymentResponse = favoritePaymentMapper.mapToDto(favoritePaymentEntity);
            FavoritePaymentResponse.Payment payment = paymentMapper.mapToDto1(paymentEntity);
            FavoritePaymentResponse.Field field = fieldMapper.mapToDto1(fieldEntity);
            favoritePaymentResponse.setPayment(payment);
            favoritePaymentResponse.setField(field);
        }
        var favoritePaymentResponseList = favoritePaymentMapper.mapToDto(favoritePaymentEntityList);
        log.info("ActionLog.getFavPayment.end: favoritePaymentResponseList {}", favoritePaymentResponseList);
        return favoritePaymentResponseList;
    }

    @Transactional
    public void createBalance(Integer id) {
        log.info("ActionLog.createBalance.start: id {}", id);

        var userEntity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var balanceEntity = balanceRepository.findByUserId(id);
        if (balanceEntity == null) {
            balanceEntity = new BalanceEntity();
            balanceEntity.setAmount(0.0);
            balanceEntity.setCashbackAmount(0.0);
        }
        balanceEntity.setUser(userEntity);
        balanceRepository.save(balanceEntity);
        userEntity.setBalance(balanceEntity);
        userRepository.save(userEntity);

        var balanceResponseDto = balanceMapper.mapToDto(balanceEntity);
        log.info("ActionLog.createBalance.end: balanceResponseDto {}", balanceResponseDto);
    }

}




