package com.example.daily_issue.calendar.mapper;

import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
import com.example.daily_issue.calendar.ro.BasicTaskResp;
import com.example.daily_issue.calendar.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskMapper {

    @Autowired
    SecurityService securityService;


    // Task Request VO --> Task DTO
    public BasicTask convertTaskReqToTask(BasicTaskReq source)
    {
        return convertTaskReqToTask(source, new BasicTask());
    }
    public BasicTask convertTaskReqToTask(BasicTaskReq source, Optional<BasicTask> target)
    {
        return target.isPresent() ? convertTaskReqToTask(source, target.get()) : null;
    }
    public BasicTask convertTaskReqToTask(BasicTaskReq source, BasicTask target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::getIsAllDay).to(target::setIsAllDay);
        propertyMapper.from(source::getColor).to(target::setColor);
        propertyMapper.from(source::getComment).to(target::setComment);
        propertyMapper.from(source::getTaskStartDate).to(target::setTaskStartDate);
        propertyMapper.from(source::getTaskStartTime).to(target::setTaskStartTime);
        propertyMapper.from(source::getTaskEndDate).to(target::setTaskEndDate);
        propertyMapper.from(source::getTaskEndTime).to(target::setTaskEndTime);
        propertyMapper.from(source::getPlace).to(target::setPlace);
        propertyMapper.from(source::getTitle).to(target::setTitle);

        return target;
    }


    // Task DTO --> Task Response VO
    public BasicTaskResp convertTaskToTaskResp(Optional<BasicTask> source)
    {
        return source.isPresent() ? convertTaskToTaskResp(source.get()) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(BasicTask source)
    {
        return convertTaskToTaskResp(source, new BasicTaskResp());
    }
    public BasicTaskResp convertTaskToTaskResp(Optional<BasicTask> source, BasicTaskResp target)
    {
        return source.isPresent() ? convertTaskToTaskResp(source.get(), target) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(BasicTask source, BasicTaskResp target)
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
        propertyMapper.from(source::getTaskStartDate).to(target::setTaskStartDate);
        propertyMapper.from(source::getTaskStartTime).to(target::setTaskStartTime);
        propertyMapper.from(source::getTaskEndDate).to(target::setTaskEndDate);
        propertyMapper.from(source::getTaskEndTime).to(target::setTaskEndTime);
        propertyMapper.from(source::getPlace).to(target::setPlace);
        propertyMapper.from(source::getTitle).to(target::setTitle);

        return target;
    }
}
