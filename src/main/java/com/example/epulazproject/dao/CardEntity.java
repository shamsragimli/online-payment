package com.example.epulazproject.dao;

import com.example.epulazproject.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;
import java.util.Random;

@Entity
@Table(name= "cards")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private Double amount = generateAmount();

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "card")
    private List<TransactionEntity> transaction;

    public Double generateAmount() {
        Random random = new Random();
        return random.nextDouble(1000) + 1;
    }
}
