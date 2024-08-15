package com.example.epulazproject.repository;

import com.example.epulazproject.dao.AutoTransactionEntity;
import com.example.epulazproject.dao.UserEntity;
import com.example.epulazproject.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AutoTransactionRepository extends JpaRepository<AutoTransactionEntity,Integer> {
    List<AutoTransactionEntity> findAllByTransactionTimeBeforeAndPaymentStatus(LocalDateTime dateTime, PaymentStatus paymentStatus);

    List<AutoTransactionEntity> findByPaymentStatus(PaymentStatus paymentStatus);

}
