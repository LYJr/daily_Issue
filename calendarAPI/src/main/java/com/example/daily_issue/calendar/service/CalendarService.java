package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.dao.CalendarRepository;
import com.example.daily_issue.calendar.domain.Task;
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
    CalendarRepository calendarRepository;



    public Optional<Task> save(@NonNull Task task)
    {
        Task result = calendarRepository.save(task);
        return Optional.ofNullable(result);
    }

    public Optional<Task> update(@NonNull Task task)
    {
        Optional<Task> originTask = Optional.ofNullable(task);

        return originTask
                .filter(t -> t.getCreatedBy().isPresent())
                .filter(t -> isTaskOwner(Optional.of(t)))
                .map(calendarRepository::save);
    }

    public Optional<Task> delete(@NonNull Long id)
    {
        Optional<Task> originTask = findByTaskId(id);

        originTask.ifPresent(a -> calendarRepository.deleteById(a.getId()));

        return originTask.isPresent() ? originTask : Optional.empty();
    }

    public Optional<Task> findByTaskId(@NonNull Long id)
    {
        Optional<Task> task = calendarRepository.findById(id);

        // 특정 일정 생성자와 현재 로그인한 사용자가 동일하지 않을 경우 null 반환
        return isTaskOwner(task) ? task : Optional.empty();
    }


    // 특정 일정 생성자가 현재 로그인한 사용자인지 확인.
    public boolean isTaskOwner(@NonNull Optional<Task> task)
    {
        return task.isPresent() ? isTaskOwner(task.get()) : false;
    }
    public boolean isTaskOwner(@NonNull Task task)
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
