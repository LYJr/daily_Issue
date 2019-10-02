package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Task, Long> {

    public List<Task> findByCreatedBy(User user);

}
