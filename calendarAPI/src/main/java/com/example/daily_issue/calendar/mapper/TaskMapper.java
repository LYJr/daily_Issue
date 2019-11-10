package com.example.daily_issue.calendar.mapper;

import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.domain.RepeatableTask;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
import com.example.daily_issue.calendar.ro.BasicTaskResp;
import com.example.daily_issue.calendar.ro.RepeatableTaskReq;
import com.example.daily_issue.calendar.ro.RepeatableTaskResp;
import com.example.daily_issue.calendar.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class TaskMapper {

    @Autowired
    SecurityService securityService;


    // Task Request RO --> Task Domain
    public BasicTask convertTaskReqToTask(BasicTaskReq source)
    {
        return source != null ? convertTaskReqToTask(source, new BasicTask()) : null;
    }
    public BasicTask convertTaskReqToTask(BasicTaskReq source, Optional<BasicTask> target)
    {
        return source != null && target.isPresent() ? convertTaskReqToTask(source, target.get()) : null;
    }
    public BasicTask convertTaskReqToTask(BasicTaskReq source, BasicTask target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::getRepeatableTaskReqs).as(reqList -> {
            Set<RepeatableTask> tempRepeatableTasks = new HashSet<>();
            reqList.forEach(req -> {
                if(req != null)
                {
                    RepeatableTask repeatableTask = convertRepeatableTaskReqToRepeatableTask(req);
                    repeatableTask.setBasicTask(target);
                    tempRepeatableTasks.add(repeatableTask);
                }
            });
            return tempRepeatableTasks;
        }).to(target::setRepeatableTasks);
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


    // Task Domain --> Task Response RO
    public BasicTaskResp convertTaskToTaskResp(Optional<BasicTask> source)
    {
        return source.isPresent() ? convertTaskToTaskResp(source.get()) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(BasicTask source)
    {
        return source != null ? convertTaskToTaskResp(source, new BasicTaskResp()) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(Optional<BasicTask> source, BasicTaskResp target)
    {
        return target != null && source.isPresent() ? convertTaskToTaskResp(source.get(), target) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(BasicTask source, BasicTaskResp target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::getRepeatableTasks).as(taskList -> {
            Set<RepeatableTaskResp> tempRepeatableTaskResps = new HashSet<>();
            taskList.forEach(task -> {
                if(task != null)
                {
                    RepeatableTaskResp taskResp = convertRepeatableTaskToRepeatableTaskResp(task);
                    tempRepeatableTaskResps.add(taskResp);
                }
            });
            return tempRepeatableTaskResps;
        }).to(target::setRepeatableTaskResps);

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















    // Repeatable Task Request RO --> Repeatable Task Domain
    public RepeatableTask convertRepeatableTaskReqToRepeatableTask(RepeatableTaskReq source)
    {
        return source != null ? convertRepeatableTaskReqToRepeatableTask(source, new RepeatableTask()) : null;
    }
    public RepeatableTask convertRepeatableTaskReqToRepeatableTask(RepeatableTaskReq source, Optional<RepeatableTask> target)
    {
        return source != null && target.isPresent() ? convertRepeatableTaskReqToRepeatableTask(source, target.get()) : null;
    }
    public RepeatableTask convertRepeatableTaskReqToRepeatableTask(RepeatableTaskReq source, RepeatableTask target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

        propertyMapper.from(source::getRepeatChronoUnit).to(target::setRepeatChronoUnit);
        propertyMapper.from(source::getRepeatAmount).to(target::setRepeatAmount);
        propertyMapper.from(source::isIncludeBaseDate).to(target::setIncludeBaseDate);
        propertyMapper.from(source::getRepeatStartDate).to(target::setRepeatStartDate);
        propertyMapper.from(source::getRepeatEndDate).to(target::setRepeatEndDate);
        propertyMapper.from(source::getRepeatDayOfWeeks).to(target::setRepeatDayOfWeeks);
        propertyMapper.from(source::getRepeatDays).to(target::setRepeatDays);


        return target;
    }


    // Repeatable Task Domain --> Repeatable Task Response RO
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(Optional<RepeatableTask> source)
    {
        return source.isPresent() ? convertRepeatableTaskToRepeatableTaskResp(source.get()) : null;
    }
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(RepeatableTask source)
    {
        return source != null ? convertRepeatableTaskToRepeatableTaskResp(source, new RepeatableTaskResp()) : null;
    }
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(Optional<RepeatableTask> source, RepeatableTaskResp target)
    {
        return target != null && source.isPresent() ? convertRepeatableTaskToRepeatableTaskResp(source.get(), target) : null;
    }
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(RepeatableTask source, RepeatableTaskResp target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::getId).to(target::setId);

        propertyMapper.from(source::getRepeatChronoUnit).to(target::setRepeatChronoUnit);
        propertyMapper.from(source::getRepeatAmount).to(target::setRepeatAmount);
        propertyMapper.from(source::isIncludeBaseDate).to(target::setIncludeBaseDate);
        propertyMapper.from(source::getRepeatStartDate).to(target::setRepeatStartDate);
        propertyMapper.from(source::getRepeatEndDate).to(target::setRepeatEndDate);
        propertyMapper.from(source::getRepeatDayOfWeeks).to(target::setRepeatDayOfWeeks);
        propertyMapper.from(source::getRepeatDays).to(target::setRepeatDays);

        return target;
    }
}
