package com.example.epulazproject.service;

import com.example.epulazproject.model.request.FavoritePaymentDto;
import com.example.epulazproject.model.request.FieldRequestDto;
import com.example.epulazproject.model.request.PaymentRequestDto;
import com.example.epulazproject.model.response.FavoritePaymentResponse;
import com.example.epulazproject.model.response.FieldResponseDto;
import com.example.epulazproject.model.response.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getAll();

    PaymentResponseDto getById(Integer id);

    PaymentResponseDto add(PaymentRequestDto paymentRequestDto);

    FieldResponseDto addFieldForPayment(FieldRequestDto fieldRequestDto);

    List<FieldResponseDto> getFieldsForPayment(Integer paymentId);

    PaymentResponseDto update(Integer id, PaymentRequestDto paymentRequestDto);

    void delete(Integer id);

    FavoritePaymentResponse createFav(FavoritePaymentDto favoritePaymentDto);

    FavoritePaymentResponse getFav(Integer id);

    List<FavoritePaymentResponse> getAllFav();


    void deleteFav(Integer id);


}
