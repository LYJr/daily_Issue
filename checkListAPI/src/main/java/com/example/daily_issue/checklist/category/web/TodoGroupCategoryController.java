package com.example.daily_issue.checklist.category.web;

import com.example.daily_issue.checklist.category.service.TodoGroupCategory;
import com.example.daily_issue.checklist.category.service.TodoGroupCategoryService;
import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class TodoGroupCategoryController {

    private TodoGroupCategoryService todoGroupCategoryService;

    public TodoGroupCategoryController(TodoGroupCategoryService todoGroupCategoryService) {
        this.todoGroupCategoryService = todoGroupCategoryService;
    }

    @GetMapping("/")
    public ResponseEntity list() {
        List<TodoGroupCategory> all = todoGroupCategoryService.findAll();

        List<TodoGroupCategory.Response> responseList = new ArrayList<>();
        if(all != null) {
            for(TodoGroupCategory category : all) {
                responseList.add(category.converToResponse());
            }
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity view(@PathVariable("id") int id) throws ObjectNotFoundException {
        return ResponseEntity.ok(todoGroupCategoryService.findById(id).converToResponse());
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoGroupCategory.Response save(@RequestBody TodoGroupCategory.Request request) {
        return todoGroupCategoryService.save(request.converToOriginal()).converToResponse();
    }

    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id,@RequestBody TodoGroupCategory.Request request) throws ObjectNotFoundException {
        return ResponseEntity.ok(todoGroupCategoryService.updateById(id, request).converToResponse());
    }


}
