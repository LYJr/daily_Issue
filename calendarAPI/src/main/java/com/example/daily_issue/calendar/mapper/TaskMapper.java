package com.example.daily_issue.calendar.mapper;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.calendar.ro.TaskReq;
import com.example.daily_issue.calendar.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskMapper {

    @Autowired
    SecurityService securityService;

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
}
