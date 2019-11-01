package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.login.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Task, Long>, RevisionRepository<Task, Long, Integer> {

    public List<Task> findByCreatedBy(Account user);

}
