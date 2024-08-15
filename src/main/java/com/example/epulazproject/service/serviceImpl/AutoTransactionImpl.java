package com.example.epulazproject.service.serviceImpl;

import com.example.epulazproject.dao.*;
import com.example.epulazproject.enums.PaymentStatus;
import com.example.epulazproject.enums.SupportStatus;
import com.example.epulazproject.exception.NotEnoughBalance;
import com.example.epulazproject.exception.NotFoundException;
import com.example.epulazproject.mapper.AutoTransactionMapper;
import com.example.epulazproject.mapper.FieldMapper;
import com.example.epulazproject.mapper.PaymentMapper;
import com.example.epulazproject.model.request.AutoTransactionReq;
import com.example.epulazproject.model.request.Email;
import com.example.epulazproject.model.response.AutoTransactionRes;
import com.example.epulazproject.model.response.SupportResponseDto;
import com.example.epulazproject.model.response.TransactionResponseDto;
import com.example.epulazproject.repository.*;
import com.example.epulazproject.service.AutoTransactionService;
import com.example.epulazproject.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoTransactionImpl implements AutoTransactionService {
    private final AutoTransactionRepository autoTransactionRepository;
    private final PaymentMapper paymentMapper;
    private final FieldMapper fieldMapper;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;
    private final BalanceRepository balanceRepository;
    private final AutoTransactionMapper autoTransactionMapper;

    final Double CASHBACK_RATE = 0.02;

    @Transactional
    public AutoTransactionRes create(AutoTransactionReq autoTransactionReq){
        var paymentEntity = paymentRepository.findById(autoTransactionReq.getPaymentId()).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var userEntity = userRepository.findById(autoTransactionReq.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var fieldEntity = fieldRepository.findById(autoTransactionReq.getFieldId()).orElseThrow(
                () -> new NotFoundException("FIELD_NOT_FOUND")
        );

        var autoTransactionEntity = autoTransactionMapper.mapToEntity(autoTransactionReq);
        autoTransactionEntity.setUser(userEntity);
        autoTransactionEntity.setPayment(paymentEntity);
        autoTransactionEntity.setField(fieldEntity);
        autoTransactionEntity.setBalance(userEntity.getBalance());
        autoTransactionEntity.setExecuted(false);
        autoTransactionEntity.setPaymentStatus(PaymentStatus.PENDING);
        autoTransactionRepository.save(autoTransactionEntity);
        var autoTransactionRes = autoTransactionMapper.mapToDto(autoTransactionEntity);
        AutoTransactionRes.Payment payment = paymentMapper.mapToDto2(paymentEntity);
        AutoTransactionRes.Field field = fieldMapper.mapToDto2(fieldEntity);
        autoTransactionRes.setPayment(payment);
        autoTransactionRes.setField(field);
        return autoTransactionRes;
    }


    @Transactional
    public List<AutoTransactionRes> getAll(){
        var autoTransactionEntityList = autoTransactionRepository.findAll();
        var list = autoTransactionMapper.mapToDto(autoTransactionEntityList);
        return list;
    }

    @Transactional
    public List<AutoTransactionRes> getFiltered(PaymentStatus paymentStatus){
        log.info("ActionLog.getFiltered.start: paymentStatus {}", paymentStatus);
        var autoTransactionList = autoTransactionRepository.findByPaymentStatus(paymentStatus);
        var autoTransactionResList = autoTransactionMapper.mapToDto(autoTransactionList);
        log.info("ActionLog.getFiltered.end: autoTransactionResList {}", autoTransactionResList);
        return autoTransactionResList;
    }




    @Transactional
    public void executeAutoTransaction(AutoTransactionEntity autoTransaction){
        if (autoTransaction.getAmount() > autoTransaction.getUser().getBalance().getAmount()) {
            autoTransaction.setPaymentStatus(PaymentStatus.FAILED);
            autoTransaction.setBalance(autoTransaction.getBalance());
            autoTransaction.setUser(autoTransaction.getUser());
            autoTransaction.setPayment(autoTransaction.getPayment());
            autoTransaction.setField(autoTransaction.getField());
            autoTransaction.setTransactionTime(LocalDateTime.now());
            autoTransactionRepository.save(autoTransaction);

            autoTransaction.getUser().getAutoTransaction().add(autoTransaction);
            userRepository.save(autoTransaction.getUser());

            autoTransaction.getBalance().getAutoTransaction().add(autoTransaction);
            balanceRepository.save(autoTransaction.getBalance());

            autoTransaction.getPayment().getAutoTransaction().add(autoTransaction);
            paymentRepository.save(autoTransaction.getPayment());
            throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");
        } else {
            autoTransaction.setPaymentStatus(PaymentStatus.SUCCEED);
            autoTransaction.getBalance().setAmount(autoTransaction.getBalance().getAmount() - autoTransaction.getAmount());
            var cashback = autoTransaction.getAmount() * CASHBACK_RATE;
            autoTransaction.getBalance().setCashbackAmount(autoTransaction.getBalance().getCashbackAmount() + cashback);
            autoTransaction.setExecuted(true);
            autoTransaction.setBalance(autoTransaction.getBalance());
            autoTransaction.setUser(autoTransaction.getUser());
            autoTransaction.setPayment(autoTransaction.getPayment());
            autoTransaction.setField(autoTransaction.getField());
            autoTransaction.setTransactionTime(LocalDateTime.now());
            autoTransactionRepository.save(autoTransaction);

            autoTransaction.getUser().getAutoTransaction().add(autoTransaction);
            userRepository.save(autoTransaction.getUser());

            autoTransaction.getBalance().getAutoTransaction().add(autoTransaction);
            balanceRepository.save(autoTransaction.getBalance());

            autoTransaction.getPayment().getAutoTransaction().add(autoTransaction);
            paymentRepository.save(autoTransaction.getPayment());
        }
        var autoTransactionRes = autoTransactionMapper.mapToDto(autoTransaction);
        log.info("ActionLog.executeAutoTransaction.end: transactionResponseDto {}", autoTransactionRes);

    }


}


