package com.example.daily_issue.calendar.mapper;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.calendar.ro.TaskReq;
import com.example.daily_issue.calendar.ro.TaskResp;
import com.example.daily_issue.calendar.security.service.SecurityService;
import com.example.daily_issue.login.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TaskMapper {

    @Autowired
    SecurityService securityService;


    // Task Request VO --> Task DTO
    public Task convertTaskReqToTask(TaskReq source)
    {
        return convertTaskReqToTask(source, new Task());
    }
    public Task convertTaskReqToTask(TaskReq source, Optional<Task> target)
    {
        return target.isPresent() ? convertTaskReqToTask(source, target.get()) : null;
    }
    public Task convertTaskReqToTask(TaskReq source, Task target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::getIsAllDay).to(target::setIsAllDay);
        propertyMapper.from(source::getColor).to(target::setColor);
        propertyMapper.from(source::getComment).to(target::setComment);
        propertyMapper.from(source::getEndDateTime).to(target::setEndDateTime);
        propertyMapper.from(source::getStartDateTime).to(target::setStartDateTime);
        propertyMapper.from(source::getPlace).to(target::setPlace);
        propertyMapper.from(source::getTitle).to(target::setTitle);

        return target;
    }


    // Task DTO --> Task Response VO
    public TaskResp convertTaskToTaskResp(Optional<Task> source)
    {
        return source.isPresent() ? convertTaskToTaskResp(source.get()) : null;
    }
    public TaskResp convertTaskToTaskResp(Task source)
    {
        return convertTaskToTaskResp(source, new TaskResp());
    }
    public TaskResp convertTaskToTaskResp(Optional<Task> source, TaskResp target)
    {
        return source.isPresent() ? convertTaskToTaskResp(source.get(), target) : null;
    }
    public TaskResp convertTaskToTaskResp(Task source, TaskResp target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::getId).to(target::setId);
        propertyMapper.from(source::getCreatedBy).as(a -> a.orElseGet(null).getId()).to(target::setCreatedBy);
        propertyMapper.from(source::getCreatedDate).as(a -> a.orElseGet(null)).to(target::setCreatedDate);
        propertyMapper.from(source::getLastModifiedBy).as(a -> a.orElseGet(null).getId()).to(target::setLastModifiedBy);
        propertyMapper.from(source::getLastModifiedDate).as(a -> a.orElseGet(null)).to(target::setLastModifiedDate);
        propertyMapper.from(source::getIsAllDay).to(target::setIsAllDay);
        propertyMapper.from(source::getColor).to(target::setColor);
        propertyMapper.from(source::getComment).to(target::setComment);
        propertyMapper.from(source::getEndDateTime).to(target::setEndDateTime);
        propertyMapper.from(source::getStartDateTime).to(target::setStartDateTime);
        propertyMapper.from(source::getPlace).to(target::setPlace);
        propertyMapper.from(source::getTitle).to(target::setTitle);

        return target;
    }
}
