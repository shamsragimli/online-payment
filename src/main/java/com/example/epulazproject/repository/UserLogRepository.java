package com.example.epulazproject.repository;

import com.example.epulazproject.dao.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog,Integer> {
}
