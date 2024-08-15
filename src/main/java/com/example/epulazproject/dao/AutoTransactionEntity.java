package com.example.epulazproject.dao;

import com.example.epulazproject.enums.AutoTransactionType;
import com.example.epulazproject.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "auto_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutoTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private AutoTransactionType autoTransactionType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    private LocalDateTime transactionTime;
    private Double amount;
    private Boolean executed;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private FieldEntity field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance_id")
    private BalanceEntity balance;

    @OneToMany(mappedBy = "autoTransaction")
    private List<TransactionEntity> transactions;


}
