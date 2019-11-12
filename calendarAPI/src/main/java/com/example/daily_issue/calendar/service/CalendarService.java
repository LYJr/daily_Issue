package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.aop.EnableOwnerCheck;
import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
import com.example.daily_issue.calendar.ro.BasicTaskResp;
import com.example.daily_issue.calendar.ro.DisplayReq;
import com.example.daily_issue.calendar.service.util.CalendarCalculator;
import com.example.daily_issue.calendar.vo.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarService {

    @Autowired
    CalendarCalculator calculator;
    @Autowired
    TaskMapper mapper;

    @Autowired
    BasicTaskService basicTaskService;
    @Autowired
    TaskDisplayService taskDisplayService;


    public Optional<BasicTaskResp> save(BasicTaskReq basicTaskReq)
    {
        Optional<BasicTaskResp> result = basicTaskService.save(basicTaskReq);
        return result;
    }

    @EnableOwnerCheck
    public Optional<BasicTaskResp> update(Long taskId, BasicTaskReq basicTaskReq)
    {
        Optional<BasicTaskResp> result = basicTaskService.update(taskId, basicTaskReq);
        return result;
    }

    @EnableOwnerCheck
    public Optional<BasicTaskResp> delete(Long taskId)
    {
        Optional<BasicTaskResp> result = basicTaskService.delete(taskId);
        return result;
    }

    @EnableOwnerCheck
    public Optional<BasicTaskResp> findByTaskId(Long taskId)
    {
        Optional<BasicTask> basicTask = basicTaskService.findByTaskId(taskId);
        BasicTaskResp result = mapper.convertTaskToTaskResp(basicTask);

        return Optional.ofNullable(result);
    }


    public void te(DisplayReq displayReq)
    {
        // get display range
        DateRange displayDateRange = taskDisplayService.getDisplayDateRange(displayReq);
    }



}
