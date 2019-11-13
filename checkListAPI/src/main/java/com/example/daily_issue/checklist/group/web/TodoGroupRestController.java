package com.example.daily_issue.checklist.group.web;

import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group")
public class TodoGroupRestController {
    private TodoGroupService todoGroupService;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;

    public TodoGroupRestController(TodoGroupService todoGroupService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.todoGroupService = todoGroupService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = {"/{id}"})
    public TodoGroup list(TodoGroup.Request request, @PathVariable Integer id) {
        return todoGroupService.findById(id).get();
    }

    @PostMapping(value = "/save", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoGroup.Response save(@RequestBody TodoGroup.Request body) throws JsonProcessingException {
        System.out.println(body);
//        TodoGroup.Request request = objectMapper.readValue(body, TodoGroup.Request.class);


        TodoGroup todoGroup = modelMapper.map(body, TodoGroup.class);
        todoGroupService.save(todoGroup);

        return modelMapper.map(todoGroup, TodoGroup.Response.class);
    }
}
