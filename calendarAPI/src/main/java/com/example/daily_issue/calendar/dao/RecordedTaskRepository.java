package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.calendar.domain.RecordedTask;
import com.example.daily_issue.login.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface RecordedTaskRepository extends JpaRepository<RecordedTask, Long>, RevisionRepository<RecordedTask, Long, Integer> {

    public List<RecordedTask> findByCreatedBy(Account user);

}
