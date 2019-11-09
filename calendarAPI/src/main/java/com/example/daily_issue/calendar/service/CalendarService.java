package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.domain.RepeatableTask;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.RepeatableTaskReq;
import com.example.daily_issue.calendar.service.util.CalendarCalculator;
import com.example.daily_issue.calendar.vo.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CalendarService {

    @Autowired
    CalendarCalculator calculator;
    @Autowired
    TaskMapper mapper;




    public DateRange getDisplayDateRange(ChronoUnit typeChronoUnit, LocalDate baseDate)
    {
        return calculator.getDisplayDateRange(typeChronoUnit, baseDate);
    }

    public DateRange getTaskableDateRange(ChronoUnit typeChronoUnit, LocalDate baseDate, RepeatableTaskReq repeatableTaskReq)
    {
        DateRange displayDateRange = getDisplayDateRange(typeChronoUnit, baseDate);
        return getTaskableDateRange(displayDateRange, repeatableTaskReq);
    }

    public DateRange getTaskableDateRange(DateRange displayDateRange, RepeatableTaskReq repeatableTaskReq)
    {
        RepeatableTask repeatableTask = mapper.convertRepeatableTaskReqToRepeatableTask(repeatableTaskReq);

        return calculator.getTaskableDateRange(displayDateRange, repeatableTask);
    }
}
