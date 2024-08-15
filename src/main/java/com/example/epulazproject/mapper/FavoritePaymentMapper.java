package com.example.epulazproject.mapper;

import com.example.epulazproject.dao.FavoritePaymentEntity;
import com.example.epulazproject.model.request.FavoritePaymentDto;
import com.example.epulazproject.model.response.FavoritePaymentResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FavoritePaymentMapper {
    public abstract FavoritePaymentEntity mapToEntity(FavoritePaymentDto favoritePaymentDto);
    public abstract FavoritePaymentResponse mapToDto(FavoritePaymentEntity favoritePaymentEntity);

    public abstract List<FavoritePaymentResponse> mapToDto(List<FavoritePaymentEntity> favoritePaymentEntityList);


}
