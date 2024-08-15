package com.example.epulazproject.repository;

import com.example.epulazproject.dao.SupportTicket;
import com.example.epulazproject.enums.SupportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupportRepository extends JpaRepository<SupportTicket,Integer> {
        List<SupportTicket> findByStatus(SupportStatus supportStatus);
}
