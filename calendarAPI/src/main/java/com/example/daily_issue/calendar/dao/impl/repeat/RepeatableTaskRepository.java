package com.example.daily_issue.calendar.dao.impl.repeat;

import com.example.daily_issue.calendar.dao.BaseRepository;
import com.example.daily_issue.calendar.domain.RepeatableTask;

import java.time.LocalDate;
import java.util.List;

public interface RepeatableTaskRepository extends BaseRepository<RepeatableTask, Long> {

    public List<RepeatableTask> findByRepeatStartDateBetween(LocalDate startDate, LocalDate endDate);

}
