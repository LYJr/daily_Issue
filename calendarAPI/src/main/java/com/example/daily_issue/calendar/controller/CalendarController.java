package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.TaskReq;
import com.example.daily_issue.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("testget")
    public String saveGet()
    {
        return "testget";
    }


    @PostMapping("testpost")
    public String savePost()
    {
        return "testpost";
    }


    @PostMapping("save")
    public Task save(TaskReq taskReq)
    {
        Task task = taskMapper.convertTaskReqToTask(taskReq, new Task());

        calendarService.save(task);

        return task;
    }

    @PutMapping("update")
    public Task update(@RequestParam Long taskId, TaskReq taskReq)
    {
        Optional<Task> originTask = calendarService.findByTaskId(taskId);
        Task task = taskMapper.convertTaskReqToTask(taskReq, originTask);

        return calendarService.update(task);
    }

    @DeleteMapping("delete")
    public Task delete(@RequestParam Long taskId)
    {

        return calendarService.delete(taskId);
    }



    /*@GetMapping("list")
    public List<Task> list()
    {
        Optional<User> user = (Optional<User>)
        if(user.isEmpty())
        {
            // throw exception..
            return List.of();
        }

        List<Task> tasks = calendarService.findByCreatedBy(user.get().getId());

        return tasks;
    }*/


}
