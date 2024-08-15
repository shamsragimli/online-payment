package com.example.epulazproject.model.response;

import com.example.epulazproject.enums.AutoTransactionType;
import com.example.epulazproject.enums.PaymentCategory;
import com.example.epulazproject.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutoTransactionRes {
    private Integer id;
    private String nickname;
    private AutoTransactionType autoTransactionType;
    private PaymentStatus paymentStatus;
    private LocalDateTime transactionTime;
    private Double amount;
    private Boolean executed;
    private Payment payment;
    private Field field;


    @Data
    public static class Payment {
        private String name;
        private PaymentCategory paymentCategory;
    }

    @Data
    public static class Field {
        private String type;
        private String value;
    }
}
