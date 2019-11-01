package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.login.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByUserId(String userId);
}
