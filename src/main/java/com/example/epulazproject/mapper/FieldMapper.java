package com.example.epulazproject.mapper;
import com.example.epulazproject.dao.FieldEntity;
import com.example.epulazproject.dao.PaymentEntity;
import com.example.epulazproject.model.request.FieldRequestDto;
import com.example.epulazproject.model.response.AutoTransactionRes;
import com.example.epulazproject.model.response.FavoritePaymentResponse;
import com.example.epulazproject.model.response.FieldResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {
    public abstract FieldEntity mapToEntity(FieldRequestDto fieldRequestDto);
    public abstract FieldResponseDto mapToDto(FieldEntity fieldEntity);
    public abstract FavoritePaymentResponse.Field mapToDto1(FieldEntity fieldEntity);

    public abstract List<FieldResponseDto> mapToDto(List<FieldEntity> fieldEntityList);

    public abstract AutoTransactionRes.Field mapToDto2(FieldEntity fieldEntity);

}
