package com.example.daily_issue.calendar.domain.mapper;

import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.calendar.domain.entity.RepeatableTaskEntity;
import com.example.daily_issue.calendar.domain.vo.req.BasicTaskReq;
import com.example.daily_issue.calendar.domain.vo.req.RepeatableTaskReq;
import com.example.daily_issue.calendar.domain.vo.resp.BasicTaskResp;
import com.example.daily_issue.calendar.domain.vo.resp.RepeatableTaskResp;
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
    public BasicTaskEntity convertTaskReqToTask(BasicTaskReq source)
    {
        return source != null ? convertTaskReqToTask(source, new BasicTaskEntity()) : null;
    }
    public BasicTaskEntity convertTaskReqToTask(BasicTaskReq source, Optional<BasicTaskEntity> target)
    {
        return source != null && target.isPresent() ? convertTaskReqToTask(source, target.get()) : null;
    }
    public BasicTaskEntity convertTaskReqToTask(BasicTaskReq source, BasicTaskEntity target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::isRepeatable).to(target::setRepeatable);
        if(source.isRepeatable())
        {
            propertyMapper.from(source::getRepeatableTaskReqs).as(reqList -> {
                Set<RepeatableTaskEntity> tempRepeatableTasks = new HashSet<>();
                reqList.forEach(req -> {
                    if(req != null)
                    {
                        RepeatableTaskEntity repeatableTask = convertRepeatableTaskReqToRepeatableTask(req);
                        tempRepeatableTasks.add(repeatableTask);
                    }
                });
                return tempRepeatableTasks;
            }).to(target::setRepeatableTasks);
        }
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
    public BasicTaskResp convertTaskToTaskResp(Optional<BasicTaskEntity> source)
    {
        return source.isPresent() ? convertTaskToTaskResp(source.get()) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(BasicTaskEntity source)
    {
        return source != null ? convertTaskToTaskResp(source, new BasicTaskResp()) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(Optional<BasicTaskEntity> source, BasicTaskResp target)
    {
        return target != null && source.isPresent() ? convertTaskToTaskResp(source.get(), target) : null;
    }
    public BasicTaskResp convertTaskToTaskResp(BasicTaskEntity source, BasicTaskResp target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::isRepeatable).to(target::setRepeatable);
        if(source.isRepeatable())
        {
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
        }
        propertyMapper.from(source::getId).to(target::setId);
        propertyMapper.from(source::getTaskOwner).as(a -> a.getId()).to(target::setTaskOwner);
        propertyMapper.from(source::getCreatedBy).as(a -> a.getId()).to(target::setCreatedBy);
        propertyMapper.from(source::getCreatedDate).to(target::setCreatedDate);
        propertyMapper.from(source::getLastModifiedBy).as(a -> a.getId()).to(target::setLastModifiedBy);
        propertyMapper.from(source::getLastModifiedDate).to(target::setLastModifiedDate);
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
    public RepeatableTaskEntity convertRepeatableTaskReqToRepeatableTask(RepeatableTaskReq source)
    {
        return source != null ? convertRepeatableTaskReqToRepeatableTask(source, new RepeatableTaskEntity()) : null;
    }
    public RepeatableTaskEntity convertRepeatableTaskReqToRepeatableTask(RepeatableTaskReq source, Optional<RepeatableTaskEntity> target)
    {
        return source != null && target.isPresent() ? convertRepeatableTaskReqToRepeatableTask(source, target.get()) : null;
    }
    public RepeatableTaskEntity convertRepeatableTaskReqToRepeatableTask(RepeatableTaskReq source, RepeatableTaskEntity target)
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
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(Optional<RepeatableTaskEntity> source)
    {
        return source.isPresent() ? convertRepeatableTaskToRepeatableTaskResp(source.get()) : null;
    }
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(RepeatableTaskEntity source)
    {
        return source != null ? convertRepeatableTaskToRepeatableTaskResp(source, new RepeatableTaskResp()) : null;
    }
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(Optional<RepeatableTaskEntity> source, RepeatableTaskResp target)
    {
        return target != null && source.isPresent() ? convertRepeatableTaskToRepeatableTaskResp(source.get(), target) : null;
    }
    public RepeatableTaskResp convertRepeatableTaskToRepeatableTaskResp(RepeatableTaskEntity source, RepeatableTaskResp target)
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
