package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
import com.example.daily_issue.calendar.ro.BasicTaskResp;
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
    public ResponseEntity<BasicTaskResp> save(@RequestBody BasicTaskReq taskReq)
    {
        Optional<BasicTaskResp> savedTask = calendarService.save(taskReq);

        HttpStatus responseCode;
        responseCode = savedTask.isPresent() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(savedTask.isPresent() ? savedTask.get() : null, responseCode);
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicTaskResp> update(@RequestParam Long taskId, @RequestBody BasicTaskReq taskReq)
    {
        // task update
        Optional<BasicTaskResp> updatedTask = calendarService.update(taskId, taskReq);

        HttpStatus responseCode;
        responseCode = updatedTask.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(updatedTask.isPresent() ? updatedTask.get() : null, responseCode);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicTaskResp> delete(@RequestParam Long taskId)
    {
        // task delete
        Optional<BasicTaskResp> deletedTask = calendarService.delete(taskId);

        HttpStatus responseCode;
        responseCode = deletedTask.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(deletedTask.isPresent() ? deletedTask.get() : null, responseCode);
    }





}
