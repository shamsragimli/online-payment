package com.example.epulazproject.model.response;

import com.example.epulazproject.enums.PaymentCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoritePaymentResponse {
    private Integer id;
    private String nickname;
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
