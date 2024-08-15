package com.example.epulazproject.service.serviceImpl;

import com.example.epulazproject.mapper.FavoritePaymentMapper;
import com.example.epulazproject.model.request.FavoritePaymentDto;
import com.example.epulazproject.model.response.FavoritePaymentResponse;
import com.example.epulazproject.repository.FavoritePaymentRepository;
import com.example.epulazproject.repository.FieldRepository;
import com.example.epulazproject.repository.PaymentRepository;
import com.example.epulazproject.exception.NotFoundException;
import com.example.epulazproject.mapper.FieldMapper;
import com.example.epulazproject.mapper.PaymentMapper;
import com.example.epulazproject.model.request.FieldRequestDto;
import com.example.epulazproject.model.response.FieldResponseDto;
import com.example.epulazproject.model.request.PaymentRequestDto;
import com.example.epulazproject.model.response.PaymentResponseDto;
import com.example.epulazproject.repository.UserRepository;
import com.example.epulazproject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final FieldRepository fieldRepository;
    private final PaymentMapper paymentMapper;
    private final FieldMapper fieldMapper;
    private final UserRepository userRepository;
    private final FavoritePaymentMapper favoritePaymentMapper;
    private final FavoritePaymentRepository favoritePaymentRepository;

    public List<PaymentResponseDto> getAll() {
        var paymentEntityList = paymentRepository.findAll();
        var paymentResponseDtoList = paymentMapper.mapToDto(paymentEntityList);
        return paymentResponseDtoList;
    }

    public PaymentResponseDto getById(Integer id) {
        log.info("ActionLog.getById.start: id {}", id);
        var paymentEntity = paymentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var paymentResponseDto = paymentMapper.mapToDto(paymentEntity);
        log.info("ActionLog.getById.end: paymentResponseDto {}", paymentResponseDto);
        return paymentResponseDto;
    }

    public PaymentResponseDto add(PaymentRequestDto paymentRequestDto) {
        log.info("ActionLog.add.start: paymentRequestDto {}", paymentRequestDto);
        var paymentEntity = paymentMapper.mapToEntity(paymentRequestDto);
        paymentRepository.save(paymentEntity);
        var paymentResponseDto = paymentMapper.mapToDto(paymentEntity);
        log.info("ActionLog.add.end: paymentResponseDto {}", paymentResponseDto);
        return paymentResponseDto;
    }

    @Transactional
    public FieldResponseDto addFieldForPayment(FieldRequestDto fieldRequestDto){
        log.info("ActionLog.addFieldForPayment.start: fieldRequestDto {}", fieldRequestDto);
        var paymentEntity = paymentRepository.findById(fieldRequestDto.getPaymentId()).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var fieldEntity = fieldMapper.mapToEntity(fieldRequestDto);
        fieldEntity.setPayment(paymentEntity);
        fieldRepository.save(fieldEntity);
        paymentEntity.getFieldEntity().add(fieldEntity);
        paymentRepository.save(paymentEntity);
        var fieldResponseDto = fieldMapper.mapToDto(fieldEntity);
        log.info("ActionLog.addFieldForPayment.end: fieldResponseDto {}", fieldResponseDto);
        return fieldResponseDto;
    }

    @Transactional
    public List<FieldResponseDto> getFieldsForPayment(Integer paymentId){
        log.info("ActionLog.getFieldForPayment.start: paymentId {}", paymentId);
        var paymentEntity = paymentRepository.findById(paymentId).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var fieldResponseDtoList = fieldMapper.mapToDto(paymentEntity.getFieldEntity());
        log.info("ActionLog.addFieldForPayment.end: fieldResponseDtoList {}", fieldResponseDtoList);
        return fieldResponseDtoList;
    }

    public PaymentResponseDto update(Integer id, PaymentRequestDto paymentRequestDto) {
        log.info("ActionLog.update.start: id, paymentRequestDto {} {}", id, paymentRequestDto);
        paymentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var paymentEntity = paymentMapper.mapToEntity(paymentRequestDto);
        paymentEntity.setId(id);
        paymentRepository.save(paymentEntity);
        var paymentResponseDto = paymentMapper.mapToDto(paymentEntity);
        log.info("ActionLog.update.end: paymentResponseDto {}", paymentResponseDto);
        return paymentResponseDto;
    }

    public void delete(Integer id) {
        log.info("ActionLog.delete.start: id {}", id);
        paymentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        paymentRepository.deleteById(id);
    }

    @Transactional
    public FavoritePaymentResponse createFav(FavoritePaymentDto favoritePaymentDto){
        log.info("ActionLog.createFav.start: favoritePaymentDto {}", favoritePaymentDto);
        var paymentEntity = paymentRepository.findById(favoritePaymentDto.getPaymentId()).orElseThrow(
                () -> new NotFoundException("PAYMENT_NOT_FOUND")
        );
        var userEntity = userRepository.findById(favoritePaymentDto.getUserId()).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        var fieldEntity = fieldRepository.findById(favoritePaymentDto.getFieldId()).orElseThrow(
                () -> new NotFoundException("FIELD_NOT_FOUND")
        );
        var favEntity = favoritePaymentMapper.mapToEntity(favoritePaymentDto);
        favEntity.setPayment(paymentEntity);
        favEntity.setUser(userEntity);
        favEntity.setField(fieldEntity);
        favoritePaymentRepository.save(favEntity);
        userEntity.getFavoritePayments().add(favEntity);
        var favoritePaymentResponse = favoritePaymentMapper.mapToDto(favEntity);
        FavoritePaymentResponse.Payment payment = paymentMapper.mapToDto1(paymentEntity);
        FavoritePaymentResponse.Field field = fieldMapper.mapToDto1(fieldEntity);
        favoritePaymentResponse.setPayment(payment);
        favoritePaymentResponse.setField(field);
        log.info("ActionLog.createFav.end: favoritePaymentResponse {}", favoritePaymentResponse);
        return favoritePaymentResponse;
    }

    @Transactional
    public FavoritePaymentResponse getFav(Integer id){
        log.info("ActionLog.getFav.start: id {}", id);
        var favEntity = favoritePaymentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("FAVORITE_PAYMENT_NOT_FOUND")
        );
        var paymentEntity = favEntity.getPayment();
        var fieldEntity = favEntity.getField();

        var favoritePaymentResponse = favoritePaymentMapper.mapToDto(favEntity);
        FavoritePaymentResponse.Payment payment = paymentMapper.mapToDto1(paymentEntity);
        FavoritePaymentResponse.Field field = fieldMapper.mapToDto1(fieldEntity);
        favoritePaymentResponse.setPayment(payment);
        favoritePaymentResponse.setField(field);
        log.info("ActionLog.getFav.end: favoritePaymentResponse {}", favoritePaymentResponse);
        return favoritePaymentResponse;
    }

    @Transactional
    public List<FavoritePaymentResponse> getAllFav(){
        var favList = favoritePaymentRepository.findAll();
        var favoritePaymentResponse = favoritePaymentMapper.mapToDto(favList);
        return favoritePaymentResponse;
    }

    public void deleteFav(Integer id){
        log.info("ActionLog.delete.start: id {}", id);
        favoritePaymentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("FAVORITE_PAYMENT_NOT_FOUND")
        );
        favoritePaymentRepository.deleteById(id);
    }





}
