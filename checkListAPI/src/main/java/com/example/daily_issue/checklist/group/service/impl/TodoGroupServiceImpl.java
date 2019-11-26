package com.example.daily_issue.checklist.group.service.impl;

import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.check.service.CheckDetailService;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("groupService")
public class TodoGroupServiceImpl implements TodoGroupService {
    private TodoGroupRepository todoGroupRepository;
    private ModelMapper modelMapper;
    private CheckDetailService checkDetailService;

    public TodoGroupServiceImpl(TodoGroupRepository todoGroupRepository, ModelMapper modelMapper, CheckDetailService checkDetailService) {
        this.todoGroupRepository = todoGroupRepository;
        this.modelMapper = modelMapper;
        this.checkDetailService = checkDetailService;
    }

    @Override
    public Optional<TodoGroup> findById(Integer id) {
        return todoGroupRepository.findById(id);
    }

    @Override
    public TodoGroup save(TodoGroup todoGroup) {
        todoGroupRepository.save(todoGroup);

        return todoGroup;
    }

    @Override
    public void deleteById(Integer id) {
        todoGroupRepository.deleteById(id);
    }

    @Override
    public TodoGroup update(TodoGroup todoGroup) {
        Optional<TodoGroup> todoGroupOptional = findById(todoGroup.getId());
        // TODO 못찾았을시
        if(todoGroupOptional.isPresent()) {

        }
        TodoGroup result = todoGroupOptional.get();
        result.setTitle(todoGroup.getTitle());
        result.setContents(todoGroup.getContents());

        return result;
    }
}
