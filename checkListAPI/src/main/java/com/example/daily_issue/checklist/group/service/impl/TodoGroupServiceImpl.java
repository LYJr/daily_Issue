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
    public TodoGroup save(TodoGroup.Request todoGroupRequest) {
        TodoGroup todoGroup = modelMapper.map(todoGroupRequest, TodoGroup.class);
        todoGroup.setCheckDetails(new ArrayList<>());

        todoGroupRepository.save(todoGroup);

        List<CheckDetail.Request> checkDetails = todoGroupRequest.getCheckDetails();
        if(checkDetails != null) {
            for(CheckDetail.Request checkDetailRequest : checkDetails) {
                CheckDetail checkDetail = modelMapper.map(checkDetailRequest, CheckDetail.class);
                todoGroup.addCheckDetail(checkDetail);

                checkDetailService.save(checkDetailRequest);
            }
        }

        return todoGroup;
    }

    @Override
    public void deleteById(Integer id) {
        todoGroupRepository.deleteById(id);
    }

    @Override
    public TodoGroup update(TodoGroup.Request todoRequest) {
        Optional<TodoGroup> todoGroupOptional = findById(todoRequest.getId());
        // TODO 못찾았을시
        if(todoGroupOptional.isPresent()) {

        }
        TodoGroup todoGroup = todoGroupOptional.get();
        todoGroup.setTitle(todoRequest.getTitle());
        todoGroup.setContents(todoRequest.getContents());

        return todoGroup;
    }
}
