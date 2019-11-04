package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.domain.RecordedTask;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.ReserveReq;
import com.example.daily_issue.calendar.ro.TaskReq;
import com.example.daily_issue.calendar.ro.TaskResp;
import com.example.daily_issue.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("calendar")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @Autowired
    TaskMapper taskMapper;


    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskResp> save(@RequestBody TaskReq taskReq, @RequestBody(required = false) ReserveReq reserveReq)
    {
        // task save
        RecordedTask task = taskMapper.convertTaskReqToTask(taskReq);
        Optional<RecordedTask> savedTask = calendarService.save(task);

        // for response
        TaskResp resp = taskMapper.convertTaskToTaskResp(savedTask);

        HttpStatus responseCode;
        responseCode = savedTask.isPresent() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(resp, responseCode);
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskResp> update(@RequestParam Long taskId, @RequestBody TaskReq taskReq)
    {
        // task update
        Optional<RecordedTask> updatedTask = calendarService.update(taskId, taskReq);

        // for response
        TaskResp resp = taskMapper.convertTaskToTaskResp(updatedTask);

        HttpStatus responseCode;
        responseCode = updatedTask.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(resp, responseCode);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskResp> delete(@RequestParam Long taskId)
    {
        // task delete
        Optional<RecordedTask> deletedTask = calendarService.delete(taskId);

        // for response
        TaskResp resp = taskMapper.convertTaskToTaskResp(deletedTask);

        HttpStatus responseCode;
        responseCode = deletedTask.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(resp, responseCode);
    }

}
