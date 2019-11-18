package com.example.daily_issue.checklist.group.web;

import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Object> view(TodoGroup.Request request) {
        Optional<TodoGroup> todoGroupOptional = todoGroupService.findById(request.getId());
        if(!todoGroupOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(todoGroupOptional.get().convertToResponse());
    }

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoGroup.Response save(@RequestBody TodoGroup.Request body) throws JsonProcessingException {
        TodoGroup todoGroup = todoGroupService.save(body.convertToOriginal());

        return todoGroup.convertToResponse();
    }

    @PostMapping(value="/{id}")
    public ResponseEntity<Object> update(@RequestBody TodoGroup.Request todoRequest) {
        TodoGroup todoGroup = todoGroupService.update(todoRequest.convertToOriginal());
        return ResponseEntity.ok().body(todoGroup.convertToResponse());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<TodoGroup> todoGroupOptional = todoGroupService.findById(id);
        if(!todoGroupOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        todoGroupService.deleteById(id);

        TodoGroup todoGroup = todoGroupOptional.get();

        return ResponseEntity.ok().body(todoGroup.convertToResponse());
    }
}
