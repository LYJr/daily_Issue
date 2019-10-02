package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
