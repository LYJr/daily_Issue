package com.example.daily_issue.checklist.group.service.impl;

import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("groupService")
public class TodoGroupServiceImpl implements TodoGroupService {
    private TodoGroupRepository todoGroupRepository;

    public TodoGroupServiceImpl(TodoGroupRepository todoGroupRepository) {
        this.todoGroupRepository = todoGroupRepository;
    }

    @Override
    public Optional<TodoGroup> findById(Integer id) {
        return todoGroupRepository.findById(id);
    }

    @Override
    public TodoGroup save(TodoGroup todoGroup) {
        return todoGroupRepository.save(todoGroup);
    }
}
