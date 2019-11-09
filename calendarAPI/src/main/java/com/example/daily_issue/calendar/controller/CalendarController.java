package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
import com.example.daily_issue.calendar.ro.BasicTaskResp;
import com.example.daily_issue.calendar.ro.RepeatableTaskReq;
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
    public ResponseEntity<BasicTaskResp> save(@RequestBody BasicTaskReq taskReq, @RequestBody(required = false) RepeatableTaskReq repeatableTaskReq)
    {
        Optional<BasicTask> savedTask = calendarService.save(task);

        HttpStatus responseCode;
        responseCode = savedTask.isPresent() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(resp, responseCode);
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicTaskResp> update(@RequestParam Long taskId, @RequestBody BasicTaskReq taskReq)
    {
        // task update
        Optional<BasicTask> updatedTask = calendarService.update(taskId, taskReq);

        // for response
        BasicTaskResp resp = taskMapper.convertTaskToTaskResp(updatedTask);

        HttpStatus responseCode;
        responseCode = updatedTask.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(resp, responseCode);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicTaskResp> delete(@RequestParam Long taskId)
    {
        // task delete
        Optional<BasicTask> deletedTask = calendarService.delete(taskId);

        // for response
        BasicTaskResp resp = taskMapper.convertTaskToTaskResp(deletedTask);

        HttpStatus responseCode;
        responseCode = deletedTask.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(resp, responseCode);
    }

}
