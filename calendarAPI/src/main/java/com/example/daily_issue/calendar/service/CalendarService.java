package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.aop.EnableOwnerCheck;
import com.example.daily_issue.calendar.dao.RecordedTaskRepository;
import com.example.daily_issue.calendar.domain.RecordedTask;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.TaskReq;
import com.example.daily_issue.calendar.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarService {

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    SecurityService securityService;

    @Autowired
    RecordedTaskRepository recordedTaskRepository;



    public Optional<RecordedTask> save(@NonNull RecordedTask task)
    {
        // save
        RecordedTask result = task != null ? recordedTaskRepository.save(task) : task;
        return Optional.ofNullable(result);
    }

    @EnableOwnerCheck
    public Optional<RecordedTask> update(@NonNull Long taskId, TaskReq taskReq)
    {
        // get existing task
        Optional<RecordedTask> originTask = findByTaskId(taskId);
        RecordedTask task = taskMapper.convertTaskReqToTask(taskReq, originTask);

        // update
        return task != null ? update(task) : Optional.empty();
    }

    @EnableOwnerCheck
    public Optional<RecordedTask> update(@NonNull RecordedTask task)
    {
        // update
        RecordedTask result = task != null ? recordedTaskRepository.save(task) : task;
        return Optional.ofNullable(result);
    }

    @EnableOwnerCheck
    public Optional<RecordedTask> delete(@NonNull Long id)
    {
        // get existing task
        Optional<RecordedTask> originTask = findByTaskId(id);

        // delete
        originTask.ifPresent(a -> recordedTaskRepository.deleteById(a.getId()));

        return originTask.isPresent() ? originTask : Optional.empty();
    }

    @EnableOwnerCheck
    public Optional<RecordedTask> delete(@NonNull RecordedTask task)
    {
        Optional<RecordedTask> originTask = Optional.ofNullable(task);
        originTask.ifPresent(t -> recordedTaskRepository.delete(t));

        return originTask.isPresent() ? originTask : Optional.empty();
    }


    public Optional<RecordedTask> findByTaskId(@NonNull Long id)
    {
        return recordedTaskRepository.findById(id);
    }




    /*public List<Task> findByCreatedBy(Long id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            return List.of();
        }
        List<Task> tasks = calendarRepository.findByCreatedBy(user.get());

        return tasks;
    }*/
}
