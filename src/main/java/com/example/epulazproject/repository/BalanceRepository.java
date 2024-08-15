package com.example.epulazproject.repository;

import com.example.epulazproject.dao.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Integer> {
    BalanceEntity findByUserId(Integer userId);

}
