package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.dao.CalendarRepository;
import com.example.daily_issue.calendar.dao.UserRepository;
import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {

    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserRepository userRepository;

    public Task save(Task task)
    {
        return calendarRepository.save(task);
    }

    public void delete(Long id)
    {
        calendarRepository.deleteById(id);
    }

    public Task findByTaskId(Long id)
    {
        Optional<Task> task = calendarRepository.findById(id);
        if(task.isEmpty())
        {
            return null;
        }
        return task.get();
    }

    public List<Task> findByCreatedBy(Long id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            return List.of();
        }
        List<Task> tasks = calendarRepository.findByCreatedBy(user.get());

        return tasks;
    }
}
