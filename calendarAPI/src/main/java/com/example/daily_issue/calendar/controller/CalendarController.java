package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.calendar.ro.TaskRO;
import com.example.daily_issue.calendar.service.CalendarService;
import com.example.daily_issue.calendar.service.UserService;
import com.example.daily_issue.login.domain.User;
import org.modelmapper.ModelMapper;
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
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    HttpSession session;

    /*
    특정 User 정보를 session에 담기 위한 handler
    현재는 spring security나 jwt나 등, 사용자 정보를 얻을 수 있는 방법이 없으므로,
    임시의 방법으로 session에 객체를 넣는다...
    */
    @GetMapping("init")
    public void init()
    {
        Optional<User> user = userService.findUser("user_id");
        session.setAttribute("UserSess", user);
    }

    @PostMapping("save")
    public Task save(TaskRO taskRO)
    {
        Task task = modelMapper.map(taskRO, Task.class);
        calendarService.save(task);

        return task;
    }

    @PostMapping("update")
    public Task update(@RequestParam Long taskId, TaskRO taskRO)
    {
        Task originTask = calendarService.findByTaskId(taskId);
        if(originTask == null)
        {
            //throw new Exception();
            return null;
        }

        originTask.setPlace("place test");

        /*Task fixedTask = modelMapper.map(taskRO, Task.class);
        fixedTask.

        Task task = new Task(id);
        task.setTitle("title 3333");*/

        return calendarService.save(originTask);
    }


    @GetMapping("list")
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
    }

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
