package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.calendar.domain.RepeatableTask;
import com.example.daily_issue.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface RepeatableTaskRepository extends JpaRepository<RepeatableTask, Long>, RevisionRepository<RepeatableTask, Long, Integer> {

    public List<RepeatableTask> findByCreatedBy(Member user);

}
