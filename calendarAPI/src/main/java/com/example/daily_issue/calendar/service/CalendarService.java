package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.aop.EnableOwnerCheck;
import com.example.daily_issue.calendar.dao.BasicTaskRepository;
import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
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
    BasicTaskRepository basicTaskRepository;



    public Optional<BasicTask> save(@NonNull BasicTask task)
    {
        // save
        BasicTask result = task != null ? basicTaskRepository.save(task) : task;
        return Optional.ofNullable(result);
    }

    @EnableOwnerCheck
    public Optional<BasicTask> update(@NonNull Long taskId, BasicTaskReq taskReq)
    {
        // get existing task
        Optional<BasicTask> originTask = findByTaskId(taskId);
        BasicTask task = taskMapper.convertTaskReqToTask(taskReq, originTask);

        // update
        return task != null ? update(task) : Optional.empty();
    }

    @EnableOwnerCheck
    public Optional<BasicTask> update(@NonNull BasicTask task)
    {
        // update
        BasicTask result = task != null ? basicTaskRepository.save(task) : task;
        return Optional.ofNullable(result);
    }

    @EnableOwnerCheck
    public Optional<BasicTask> delete(@NonNull Long id)
    {
        // get existing task
        Optional<BasicTask> originTask = findByTaskId(id);

        // delete
        originTask.ifPresent(a -> basicTaskRepository.deleteById(a.getId()));

        return originTask.isPresent() ? originTask : Optional.empty();
    }

    @EnableOwnerCheck
    public Optional<BasicTask> delete(@NonNull BasicTask task)
    {
        Optional<BasicTask> originTask = Optional.ofNullable(task);
        originTask.ifPresent(t -> basicTaskRepository.delete(t));

        return originTask.isPresent() ? originTask : Optional.empty();
    }


    public Optional<BasicTask> findByTaskId(@NonNull Long id)
    {
        return basicTaskRepository.findById(id);
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
