package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.calendar.domain.mapper.TaskMapper;
import com.example.daily_issue.calendar.domain.vo.DateRange;
import com.example.daily_issue.calendar.domain.vo.req.BasicTaskReq;
import com.example.daily_issue.calendar.domain.vo.req.DisplayReq;
import com.example.daily_issue.calendar.domain.vo.resp.BasicTaskResp;
import com.example.daily_issue.calendar.security.aop.EnableOwnerCheck;
import com.example.daily_issue.calendar.service.util.CalendarCalculator;
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
        Optional<BasicTaskEntity> basicTask = basicTaskService.findByTaskId(taskId);
        BasicTaskResp result = mapper.convertTaskToTaskResp(basicTask);

        return Optional.ofNullable(result);
    }


    public void te(DisplayReq displayReq)
    {
        // get display range
        DateRange displayDateRange = taskDisplayService.getDisplayDateRange(displayReq);
    }



}
