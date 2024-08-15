package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.PaymentEntity;
import com.example.epulazproject.model.request.PaymentRequestDto;
import com.example.epulazproject.model.response.AutoTransactionRes;
import com.example.epulazproject.model.response.FavoritePaymentResponse;
import com.example.epulazproject.model.response.PaymentResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    public abstract List<PaymentResponseDto> mapToDto(List<PaymentEntity> paymentEntityList);

    public abstract PaymentResponseDto mapToDto(PaymentEntity paymentEntity);

    public abstract PaymentEntity mapToEntity(PaymentResponseDto paymentResponseDto);
    public abstract PaymentEntity mapToEntity(PaymentRequestDto paymentRequestDto);

    public abstract FavoritePaymentResponse.Payment mapToDto1(PaymentEntity paymentEntity);

    public abstract AutoTransactionRes.Payment mapToDto2(PaymentEntity paymentEntity);
}
