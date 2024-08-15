package com.example.epulazproject.scheduler;

import com.example.epulazproject.dao.AutoTransactionEntity;
import com.example.epulazproject.enums.PaymentStatus;
import com.example.epulazproject.repository.AutoTransactionRepository;
import com.example.epulazproject.service.AutoTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {
    private final AutoTransactionRepository autoTransactionRepository;
    private final AutoTransactionService autoTransactionService;


    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndExecuteAutoTransactions() {
        LocalDateTime now = LocalDateTime.now();
        List<AutoTransactionEntity> transactionsToExecute = autoTransactionRepository.findAllByTransactionTimeBeforeAndPaymentStatus(now, PaymentStatus.PENDING);
        for (AutoTransactionEntity transaction : transactionsToExecute) {
            if(transaction.getTransactionTime().isBefore(LocalDateTime.now())) {
                autoTransactionService.executeAutoTransaction(transaction);
            }
        }
    }
}
