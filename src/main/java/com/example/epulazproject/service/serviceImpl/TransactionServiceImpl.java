package com.example.epulazproject.service.serviceImpl;

import com.example.epulazproject.dao.*;
import com.example.epulazproject.enums.TransactionType;
import com.example.epulazproject.exception.ForbiddenException;
import com.example.epulazproject.exception.NotEnoughBalance;
import com.example.epulazproject.exception.NotFoundException;
import com.example.epulazproject.exception.ValidationException;
import com.example.epulazproject.mapper.TransactionMapper;
import com.example.epulazproject.enums.PaymentStatus;
import com.example.epulazproject.model.request.*;
import com.example.epulazproject.model.response.TransactionResponseDto;
import com.example.epulazproject.repository.*;
import com.example.epulazproject.service.EmailService;
import com.example.epulazproject.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;
    private final BalanceRepository balanceRepository;
    private final FieldRepository fieldRepository;
    private final FavoritePaymentRepository favoritePaymentRepository;
    private final EmailService emailService;

    final Double CASHBACK_RATE = 0.02;

    @Transactional
    public TransactionResponseDto createTransactionWithCard(TransactionCardDto transactionCardDto) {
        log.info("ActionLog.createTransactionWithCard.start: transactionCardDto {}", transactionCardDto);

        var userEntity = userRepository.findById(transactionCardDto.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var cardEntity = cardRepository.findById(transactionCardDto.getCardId()).orElseThrow(
                () -> new NotFoundException("CARD_NOT_FOUND")
        );

        var paymentEntity = paymentRepository.findById(transactionCardDto.getPaymentId()).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var fieldEntity = fieldRepository.findById(transactionCardDto.getFieldId()).orElseThrow(
                () -> new NotFoundException("PAYMENT_FIELD_NOT_FOUND")
        );

        var transactionEntity = transactionMapper.mapToEntity(transactionCardDto);

        if (!userEntity.getCards().contains(cardEntity)) {
            throw new ForbiddenException("The card does not belong to the user.");
        }

        if (transactionCardDto.getAmount() > cardEntity.getAmount()) {
            transactionEntity.setPaymentStatus(PaymentStatus.FAILED);
            transactionRepository.save(transactionEntity);
            setTransaction(transactionEntity, cardEntity, paymentEntity, userEntity, fieldEntity);
            throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");

        } else {
            transactionEntity.setPaymentStatus(PaymentStatus.SUCCEED);
            cardEntity.setAmount(cardEntity.getAmount() - transactionCardDto.getAmount());
            var cashback = transactionCardDto.getAmount() * CASHBACK_RATE;
            userEntity.getBalance().setCashbackAmount(userEntity.getBalance().getCashbackAmount() + cashback);
            setTransaction(transactionEntity, cardEntity, paymentEntity, userEntity, fieldEntity);
        }
        var transactionResponseDto = transactionMapper.mapToDto(transactionEntity);
        transactionResponseDto.setUserId(userEntity.getId());
        log.info("ActionLog.createTransactionWithCard.end: transactionResponseDto {}", transactionResponseDto);
        sendEmail(transactionResponseDto);
        return transactionResponseDto;
    }


    @Transactional
    public TransactionResponseDto createTransactionWithBalance(TransactionBalanceDto transactionBalanceDto) {
        log.info("ActionLog.createTransactionWithBalance.start: transactionBalanceDto {}", transactionBalanceDto);

        var userEntity = userRepository.findById(transactionBalanceDto.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var balanceEntity = balanceRepository.findById(userEntity.getBalance().getId()).orElseThrow(
                () -> new NotFoundException("BALANCE_NOT_FOUND")
        );
        var paymentEntity = paymentRepository.findById(transactionBalanceDto.getPaymentId()).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var fieldEntity = fieldRepository.findById(transactionBalanceDto.getFieldId()).orElseThrow(
                () -> new NotFoundException("PAYMENT_FIELD_NOT_FOUND")
        );
        var transactionEntity = transactionMapper.mapToEntity(transactionBalanceDto);

        if (transactionBalanceDto.getAmount() > balanceEntity.getAmount()) {
            transactionEntity.setPaymentStatus(PaymentStatus.FAILED);
            setTransaction(transactionEntity, balanceEntity, paymentEntity, userEntity, fieldEntity);
            throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");
        } else {
            transactionEntity.setPaymentStatus(PaymentStatus.SUCCEED);
            balanceEntity.setAmount(balanceEntity.getAmount() - transactionBalanceDto.getAmount());
            var cashback = transactionBalanceDto.getAmount() * CASHBACK_RATE;
            userEntity.getBalance().setCashbackAmount(userEntity.getBalance().getCashbackAmount() + cashback);
            setTransaction(transactionEntity, balanceEntity, paymentEntity, userEntity, fieldEntity);
        }
        var transactionResponseDto = transactionMapper.mapToDto(transactionEntity);
        transactionResponseDto.setUserId(userEntity.getId());
        log.info("ActionLog.createTransactionWithBalance.end: transactionResponseDto {}", transactionResponseDto);
        sendEmail(transactionResponseDto);
        return transactionResponseDto;
    }

    @Transactional
    public TransactionResponseDto createTransactionWithCardForFavPayment(TransactionCardForFavPayment transactionCardForFavPayment) {
        log.info("ActionLog.transactionCardForFavPayment.start: transactionCardForFavPayment {}", transactionCardForFavPayment);

        var userEntity = userRepository.findById(transactionCardForFavPayment.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var cardEntity = cardRepository.findById(transactionCardForFavPayment.getCardId()).orElseThrow(
                () -> new NotFoundException("CARD_NOT_FOUND")
        );
        var favoritePaymentEntity = favoritePaymentRepository.findById(transactionCardForFavPayment.getFavoritePaymentId()).orElseThrow(
                () -> new NotFoundException("FAVORITE_PAYMENT_NOT_FOUND")
        );

        var transactionEntity = transactionMapper.mapToEntity(transactionCardForFavPayment);

        if (!userEntity.getCards().contains(cardEntity)) {
            throw new ForbiddenException("The card does not belong to the user.");
        }

        if (transactionCardForFavPayment.getAmount() > cardEntity.getAmount()) {
            transactionEntity.setPaymentStatus(PaymentStatus.FAILED);
            transactionRepository.save(transactionEntity);
            setTransaction(transactionEntity, cardEntity, favoritePaymentEntity, userEntity);
            throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");

        } else {
            transactionEntity.setPaymentStatus(PaymentStatus.SUCCEED);
            cardEntity.setAmount(cardEntity.getAmount() - transactionCardForFavPayment.getAmount());
            var cashback = transactionCardForFavPayment.getAmount() * CASHBACK_RATE;
            userEntity.getBalance().setCashbackAmount(userEntity.getBalance().getCashbackAmount() + cashback);
            setTransaction(transactionEntity, cardEntity, favoritePaymentEntity, userEntity);
        }
        var transactionResponseDto = transactionMapper.mapToDto(transactionEntity);
        transactionResponseDto.setUserId(userEntity.getId());
        log.info("ActionLog.transactionCardForFavPayment.end: transactionResponseDto {}", transactionResponseDto);
        sendEmail(transactionResponseDto);
        return transactionResponseDto;
    }

    @Transactional
    public TransactionResponseDto createTransactionWithBalanceForFavPayment(TransactionBalanceForFavPayment transactionBalanceForFavPayment) {
        log.info("ActionLog.transactionBalanceForFavPayment.start: transactionBalanceForFavPayment {}", transactionBalanceForFavPayment);

        var userEntity = userRepository.findById(transactionBalanceForFavPayment.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var balanceEntity = balanceRepository.findById(transactionBalanceForFavPayment.getBalanceId()).orElseThrow(
                () -> new NotFoundException("BALANCE_NOT_FOUND")
        );
        var favoritePaymentEntity = favoritePaymentRepository.findById(transactionBalanceForFavPayment.getFavoritePaymentId()).orElseThrow(
                () -> new NotFoundException("FAVORITE_PAYMENT_NOT_FOUND")
        );

        var transactionEntity = transactionMapper.mapToEntity(transactionBalanceForFavPayment);

        if (transactionBalanceForFavPayment.getAmount() > balanceEntity.getAmount()) {
            transactionEntity.setPaymentStatus(PaymentStatus.FAILED);
            transactionRepository.save(transactionEntity);
            setTransaction(transactionEntity, balanceEntity, favoritePaymentEntity, userEntity);
            throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");

        } else {
            transactionEntity.setPaymentStatus(PaymentStatus.SUCCEED);
            balanceEntity.setAmount(balanceEntity.getAmount() - transactionBalanceForFavPayment.getAmount());
            var cashback = transactionBalanceForFavPayment.getAmount() * CASHBACK_RATE;
            userEntity.getBalance().setCashbackAmount(userEntity.getBalance().getCashbackAmount() + cashback);
            setTransaction(transactionEntity, balanceEntity, favoritePaymentEntity, userEntity);
        }
        var transactionResponseDto = transactionMapper.mapToDto(transactionEntity);
        transactionResponseDto.setUserId(userEntity.getId());
        log.info("ActionLog.transactionBalanceForFavPayment.end: transactionResponseDto {}", transactionResponseDto);
        sendEmail(transactionResponseDto);
        return transactionResponseDto;
    }


    @Transactional
    public TransactionResponseDto createTransactionCardToCard(TransactionCardToCard transactionCardToCard) {
        log.info("ActionLog.createTransactionCardToCard.start: transactionCardToCard {}", transactionCardToCard);
        var userEntity = userRepository.findById(transactionCardToCard.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var fromCardEntity = cardRepository.findById(transactionCardToCard.getFromCardId()).orElseThrow(
                () -> new NotFoundException("CARD_NOT_FOUND")
        );
        var toCardEntity = cardRepository.findById(transactionCardToCard.getToCardId()).orElseThrow(
                () -> new NotFoundException("SECOND_CARD_NOT_FOUND")
        );

        if (!userEntity.getCards().contains(fromCardEntity)) {
            throw new ForbiddenException("The card does not belong to the user.");
        }

        var transactionEntity = transactionMapper.mapToEntity1(transactionCardToCard);
        if (Objects.equals(fromCardEntity.getId(), toCardEntity.getId())) {
            throw new ValidationException("Transfer to the same card is not allowed!");
        } else {
            if (fromCardEntity.getAmount() < transactionEntity.getAmount()) {
                transactionEntity.setPaymentStatus(PaymentStatus.FAILED);
                throw new NotEnoughBalance("NOT_ENOUGH_BALANCE");
            }
            fromCardEntity.setAmount(fromCardEntity.getAmount() - transactionEntity.getAmount());
            toCardEntity.setAmount(toCardEntity.getAmount() + transactionEntity.getAmount());
            transactionEntity.setPaymentStatus(PaymentStatus.SUCCEED);
            transactionEntity.setCard(fromCardEntity);
            transactionEntity.setUser(userEntity);
            transactionEntity.setTimestamp(LocalDateTime.now());
            transactionRepository.save(transactionEntity);

            userEntity.getTransactions().add(transactionEntity);
            userRepository.save(userEntity);

            fromCardEntity.getTransaction().add(transactionEntity);
            cardRepository.save(fromCardEntity);
        }
        var transactionResponseDto = transactionMapper.mapToDto(transactionEntity);
        transactionResponseDto.setUserId(userEntity.getId());
        log.info("ActionLog.createTransactionCardToCard.end: transactionResponseDto {}", transactionResponseDto);
        sendEmail(transactionResponseDto);
        return transactionResponseDto;


    }

    public TransactionResponseDto getById(Integer id) {
        log.info("ActionLog.getById.start: id {}", id);
        var entity = transactionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("TRANSACTION_NOT_FOUND")
        );
        Integer userId = entity.getUser().getId();
        var transactionResponseDto = transactionMapper.mapToDto(entity);
        transactionResponseDto.setUserId(userId);
        log.info("ActionLog.getById.end: transactionResponseDto {}", transactionResponseDto);
        return transactionResponseDto;

    }

    public List<TransactionResponseDto> getByType(TransactionType transactionType) {
        log.info("ActionLog.getByType.start: transactionType {}", transactionType);
        List<TransactionEntity> transactionEntityList = transactionRepository.findByTransactionType(transactionType);
        List<TransactionResponseDto> transactionResponseDtoList = transactionMapper.mapToDto(transactionEntityList);
        log.info("ActionLog.getByType.end: transactionResponseDtoList {}", transactionResponseDtoList);
        return transactionResponseDtoList;
    }

    public List<TransactionResponseDto> getAll() {
        List<TransactionEntity> transactionEntityList = transactionRepository.findAll();
        var transactionResponseDtoList = transactionMapper.mapToDto(transactionEntityList);
        return transactionResponseDtoList;
    }








    private void setTransaction(TransactionEntity transactionEntity, CardEntity cardEntity, PaymentEntity paymentEntity, UserEntity userEntity, FieldEntity fieldEntity) {
        transactionEntity.setCard(cardEntity);
        transactionEntity.setUser(userEntity);
        transactionEntity.setPayment(paymentEntity);
        transactionEntity.setField(fieldEntity);
        transactionEntity.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transactionEntity);

        userEntity.getTransactions().add(transactionEntity);
        userRepository.save(userEntity);

        cardEntity.getTransaction().add(transactionEntity);
        cardRepository.save(cardEntity);

        paymentEntity.getTransactions().add(transactionEntity);
        paymentRepository.save(paymentEntity);
    }

    private void setTransaction(TransactionEntity transactionEntity, CardEntity cardEntity, FavoritePaymentEntity favoritePaymentEntity, UserEntity userEntity) {
        transactionEntity.setCard(cardEntity);
        transactionEntity.setUser(userEntity);
        transactionEntity.setFavoritePayment(favoritePaymentEntity);
        transactionEntity.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transactionEntity);

        userEntity.getTransactions().add(transactionEntity);
        userRepository.save(userEntity);

        cardEntity.getTransaction().add(transactionEntity);
        cardRepository.save(cardEntity);

        favoritePaymentEntity.getTransactions().add(transactionEntity);
        favoritePaymentRepository.save(favoritePaymentEntity);
    }

    private void setTransaction(TransactionEntity transactionEntity, BalanceEntity balanceEntity, FavoritePaymentEntity favoritePaymentEntity, UserEntity userEntity) {
        transactionEntity.setBalance(balanceEntity);
        transactionEntity.setUser(userEntity);
        transactionEntity.setFavoritePayment(favoritePaymentEntity);
        transactionEntity.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transactionEntity);

        userEntity.getTransactions().add(transactionEntity);
        userRepository.save(userEntity);

        balanceEntity.getTransaction().add(transactionEntity);
        balanceRepository.save(balanceEntity);

        favoritePaymentEntity.getTransactions().add(transactionEntity);
        favoritePaymentRepository.save(favoritePaymentEntity);
    }

    private void setTransaction(TransactionEntity transactionEntity, BalanceEntity balanceEntity, PaymentEntity paymentEntity, UserEntity userEntity, FieldEntity fieldEntity) {
        transactionEntity.setBalance(balanceEntity);
        transactionEntity.setUser(userEntity);
        transactionEntity.setPayment(paymentEntity);
        transactionEntity.setField(fieldEntity);
        transactionEntity.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transactionEntity);

        userEntity.getTransactions().add(transactionEntity);
        userRepository.save(userEntity);

        balanceEntity.getTransaction().add(transactionEntity);
        balanceRepository.save(balanceEntity);

        paymentEntity.getTransactions().add(transactionEntity);
        paymentRepository.save(paymentEntity);
    }

    private String toReceiptString(TransactionResponseDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "====================\n" +
                "      RECEIPT       \n" +
                "====================\n" +
                "Amount:          " + String.format("%.2f", dto.getAmount()) + "\n" +
                "Date/Time:       " + dto.getTimestamp().format(formatter) + "\n" +
                "Transaction Type:" + dto.getTransactionType() + "\n" +
                "Payment Status:  " + dto.getPaymentStatus() + "\n" +
                "====================";
    }

    private void sendEmail(TransactionResponseDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String text = toReceiptString(dto);
        var userEntity = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        Email email = new Email();
        email.setReceiver(userEntity.getEmail());
        email.setSubject("TRANSACTION IN TIME: " + dto.getTimestamp().format(formatter) + " BY E-PUL.AZ");
        email.setText(text);
        emailService.sendSimpleEmail(email);
    }


}
