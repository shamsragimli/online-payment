package com.example.epulazproject.model.response;

import com.example.epulazproject.enums.PaymentCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDto {
    private Integer id;
    private String name;
    private PaymentCategory paymentCategory;


}
