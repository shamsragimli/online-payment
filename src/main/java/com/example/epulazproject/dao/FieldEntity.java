package com.example.epulazproject.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name= "fields")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @OneToOne(mappedBy = "field", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FavoritePaymentEntity favoritePayments;

    @OneToMany(mappedBy = "field")
    private List<TransactionEntity> transactions;
}
