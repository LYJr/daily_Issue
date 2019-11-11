package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.time.LocalDate;
import java.util.List;

public interface BasicTaskRepository extends JpaRepository<BasicTask, Long>, RevisionRepository<BasicTask, Long, Integer> {

    public List<BasicTask> findByCreatedBy(Member user);

    public List<BasicTask> findByCreatedByAndTaskStartDateBetween(Member user, LocalDate startDate, LocalDate endDate);

}
