package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.calendar.domain.ReservedTask;
import com.example.daily_issue.login.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface ReservedTaskRepository extends JpaRepository<ReservedTask, Long>, RevisionRepository<ReservedTask, Long, Integer> {

    public List<ReservedTask> findByCreatedBy(Account user);

}
