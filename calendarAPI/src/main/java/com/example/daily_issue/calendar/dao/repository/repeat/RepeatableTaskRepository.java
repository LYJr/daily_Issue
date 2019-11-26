package com.example.daily_issue.calendar.dao.repository.repeat;

import com.example.daily_issue.calendar.dao.repository.BaseRepository;
import com.example.daily_issue.calendar.domain.entity.RepeatableTaskEntity;

import java.time.LocalDate;
import java.util.List;

public interface RepeatableTaskRepository extends BaseRepository<RepeatableTaskEntity, Long> {

    public List<RepeatableTaskEntity> findByRepeatStartDateBetween(LocalDate startDate, LocalDate endDate);

}
