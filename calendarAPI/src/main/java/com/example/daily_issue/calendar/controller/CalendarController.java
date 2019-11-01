package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.TaskReq;
import com.example.daily_issue.calendar.ro.TaskResp;
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


    @PostMapping("save")
    public TaskResp save(TaskReq taskReq)
    {
        Task task = taskMapper.convertTaskReqToTask(taskReq);

        Optional<Task> savedTask = calendarService.save(task);

        return taskMapper.convertTaskToTaskResp(savedTask);
    }

    @PutMapping("update")
    public TaskResp update(@RequestParam Long taskId, TaskReq taskReq)
    {
        Optional<Task> originTask = calendarService.findByTaskId(taskId);
        Task task = taskMapper.convertTaskReqToTask(taskReq, originTask);

        Optional<Task> updatedTask = calendarService.update(task);

        return taskMapper.convertTaskToTaskResp(updatedTask);
    }

    @DeleteMapping("delete")
    public TaskResp delete(@RequestParam Long taskId)
    {
        Optional<Task> deletedTask = calendarService.delete(taskId);

        return taskMapper.convertTaskToTaskResp(deletedTask);
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
