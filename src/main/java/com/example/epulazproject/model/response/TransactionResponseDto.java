package com.example.epulazproject.model.response;

import com.example.epulazproject.enums.PaymentStatus;
import com.example.epulazproject.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponseDto {
    private Integer id;
    private Double amount;
    private LocalDateTime timestamp;
    private TransactionType transactionType;
    private PaymentStatus paymentStatus;
    private Integer userId;

}
