package com.example.daily_issue.calendar.mapper;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.calendar.ro.TaskRO;
import com.example.daily_issue.login.domain.User;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {


    public Task convertTaskROtoTask(TaskRO source, Task target)
    {
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from(source::getIsAllDay).to(target::setIsAllDay);
        propertyMapper.from(source::getColor).to(target::setColor);
        propertyMapper.from(source::getComment).to(target::setComment);
        propertyMapper.from(source::getEndDateTime).to(target::setEndDateTime);
        propertyMapper.from(source::getStartDateTime).to(target::setStartDateTime);
        propertyMapper.from(source::getPlace).to(target::setPlace);
        propertyMapper.from(source::getTitle).to(target::setTitle);
        //propertyMapper.from(source::getTaskPerformerId).as(userService::findUser).to(target::setTaskPerformer);
        propertyMapper.from(User::new).to(target::setTaskPerformer);

        return target;
    }
}
