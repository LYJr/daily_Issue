package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.calendar.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Task, Long> {

}
