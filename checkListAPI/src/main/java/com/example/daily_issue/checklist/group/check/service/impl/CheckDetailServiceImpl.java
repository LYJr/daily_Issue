package com.example.daily_issue.checklist.group.check.service.impl;

import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.check.service.CheckDetailService;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import com.example.daily_issue.checklist.group.service.impl.TodoGroupRepository;
import com.example.daily_issue.checklist.group.service.impl.TodoGroupServiceImpl;
import org.hibernate.annotations.Check;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("checkDetailService")
public class CheckDetailServiceImpl implements CheckDetailService {
    private TodoGroupRepository todoGroupRepository;
    private CheckDetailRepository checkDetailRepository;
    private ModelMapper modelMapper;

    public CheckDetailServiceImpl(CheckDetailRepository checkDetailRepository, TodoGroupRepository todoGroupRepository, ModelMapper modelMapper) {
        this.checkDetailRepository = checkDetailRepository;
        this.todoGroupRepository = todoGroupRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CheckDetail> findAll() {
        return checkDetailRepository.findAll();
    }

    @Override
    public List<CheckDetail> findAllByTodoGroupId(Integer todoGroupId) {
        return checkDetailRepository.findAllByTodoGroupId(todoGroupId);
    }

    @Override
    public CheckDetail save(CheckDetail.Request request) {
        Optional<TodoGroup> todoGroupOptional = todoGroupRepository.findById(request.getTodoGroupId());
        // TODO 없을시 처리


        CheckDetail checkDetail = modelMapper.map(request, CheckDetail.class);

        TodoGroup todoGroup = todoGroupOptional.get();
//        todoGroup.addCheckDetail(checkDetail);

        return checkDetail;
    }
}
