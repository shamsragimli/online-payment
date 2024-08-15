package com.example.epulazproject.dao;

import com.example.epulazproject.enums.PaymentCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name= "payments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    private PaymentCategory paymentCategory;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<FieldEntity> fieldEntity;

    @OneToMany(mappedBy = "payment")
    private List<TransactionEntity> transactions;

    @OneToMany(mappedBy = "payment")
    private List<AutoTransactionEntity> autoTransaction;

    @OneToMany(mappedBy = "payment")
    private List<FavoritePaymentEntity> favoritePayments;


}
