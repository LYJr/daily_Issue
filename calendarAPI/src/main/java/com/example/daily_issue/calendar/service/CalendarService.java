package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.dao.RecordedTaskRepository;
import com.example.daily_issue.calendar.domain.RecordedTask;
import com.example.daily_issue.calendar.security.service.SecurityService;
import com.example.daily_issue.login.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarService {

    @Autowired
    SecurityService securityService;

    @Autowired
    RecordedTaskRepository recordedTaskRepository;



    public Optional<RecordedTask> save(@NonNull RecordedTask task)
    {
        RecordedTask result = recordedTaskRepository.save(task);
        return Optional.ofNullable(result);
    }

    public Optional<RecordedTask> update(@NonNull RecordedTask task)
    {
        Optional<RecordedTask> originTask = Optional.ofNullable(task);

        return originTask
                .filter(t -> t.getCreatedBy().isPresent())
                .filter(t -> isTaskOwner(Optional.of(t)))
                .map(recordedTaskRepository::save);
    }

    public Optional<RecordedTask> delete(@NonNull Long id)
    {
        Optional<RecordedTask> originTask = findByTaskId(id);

        originTask.ifPresent(a -> recordedTaskRepository.deleteById(a.getId()));

        return originTask.isPresent() ? originTask : Optional.empty();
    }

    public Optional<RecordedTask> findByTaskId(@NonNull Long id)
    {
        Optional<RecordedTask> task = recordedTaskRepository.findById(id);

        // 특정 일정 생성자와 현재 로그인한 사용자가 동일하지 않을 경우 null 반환
        return isTaskOwner(task) ? task : Optional.empty();
    }


    // 특정 일정 생성자가 현재 로그인한 사용자인지 확인.
    public boolean isTaskOwner(@NonNull Optional<RecordedTask> task)
    {
        return task.isPresent() ? isTaskOwner(task.get()) : false;
    }
    public boolean isTaskOwner(@NonNull RecordedTask task)
    {
        Optional<String> userId = task.getCreatedBy()
                .map(Account::getUserId)
                .filter(id -> id.equals(securityService.getAccount().getUserId()));

        return userId.isPresent();
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
