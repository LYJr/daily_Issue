package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.TaskRO;
import com.example.daily_issue.calendar.service.CalendarService;
import com.example.daily_issue.login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("calendar")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    /*@Autowired
    ModelMapper modelMapper;*/
    @Autowired
    TaskMapper taskMapper;

    @Autowired
    HttpSession session;


    @PostMapping("save")
    public Task save(TaskRO taskRO)
    {
        taskRO.setTaskPerformerId("user_id");
        Task task = taskMapper.convertTaskROtoTask(taskRO, new Task());

        calendarService.save(task);

        return task;
    }

    @PutMapping("update")
    public Task update(@RequestParam Long taskId, TaskRO taskRO)
    {
        Task originTask = calendarService.findByTaskId(taskId);
        if(originTask == null)
        {
            //throw new Exception();
            return null;
        }

        Task task = taskMapper.convertTaskROtoTask(taskRO, originTask);

        return calendarService.save(task);
    }

    @DeleteMapping("delete")
    public Task delete(@RequestParam Long taskId)
    {
        Task originTask = calendarService.findByTaskId(taskId);
        if(originTask == null)
        {
            //throw new Exception();
            return null;
        }

        calendarService.delete(taskId);

        return originTask;
    }



    /*@GetMapping("list")
    public List<Task> list()
    {
        Optional<User> user = (Optional<User>) session.getAttribute("UserSess");
        if(user.isEmpty())
        {
            // throw exception..
            return List.of();
        }

        List<Task> tasks = calendarService.findByCreatedBy(user.get().getId());

        return tasks;
    }*/

    @GetMapping("user")
    public User user()
    {
        Optional<User> user = (Optional<User>) session.getAttribute("UserSess");
        if(user.isEmpty())
        {
            // throw exception..
            return null;
        }

        return user.get();
    }
}
