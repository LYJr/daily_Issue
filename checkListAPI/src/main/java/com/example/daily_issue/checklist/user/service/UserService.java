package com.example.daily_issue.checklist.user.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User,String> {
}
