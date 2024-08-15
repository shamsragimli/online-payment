package com.example.epulazproject.repository;

import com.example.epulazproject.dao.TransactionEntity;
import com.example.epulazproject.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Integer> {
    List<TransactionEntity> findByTransactionType(TransactionType transactionType);

    List<TransactionEntity> findByUserId(Integer userId);
    List<TransactionEntity> findByTimestampAfter(LocalDateTime date);
    List<TransactionEntity> findByUserIdAndTimestampAfter(Integer userId, LocalDateTime date);
}
