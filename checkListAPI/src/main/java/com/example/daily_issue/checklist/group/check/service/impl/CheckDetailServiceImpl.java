package com.example.daily_issue.checklist.group.check.service.impl;

import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;
import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.check.service.CheckDetailService;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import com.example.daily_issue.checklist.group.service.impl.TodoGroupRepository;
import com.example.daily_issue.checklist.group.service.impl.TodoGroupServiceImpl;
import org.hibernate.annotations.Check;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    public CheckDetail save(Integer todoGroupId,CheckDetail checkDetail) throws ObjectNotFoundException {
        Optional<TodoGroup> todoGroupOptional = todoGroupRepository.findById(todoGroupId);
        if(!todoGroupOptional.isPresent()) {
            throw new ObjectNotFoundException();
        }

        TodoGroup todoGroup = todoGroupOptional.get();
        todoGroup.addCheckDetail(checkDetail);

        return checkDetail;
    }

    @Override
    public CheckDetail updateByTodoGroupIdAndId(Integer todoGroupId, Integer id, CheckDetail checkDetail) throws ObjectNotFoundException {
        Optional<CheckDetail> byId = checkDetailRepository.findByTodoGroupIdAndId(todoGroupId, id);

        if(!byId.isPresent()) {
            throw new ObjectNotFoundException();
        }

        CheckDetail checkDetailResult = byId.get();
        checkDetailResult.setContents(checkDetail.getContents());
        checkDetailResult.setTitle(checkDetail.getTitle());

        return checkDetailResult;
    }

    @Override
    public CheckDetail updateCompleteByTodoGroupIdAndId(Integer todoGroupId, Integer id, boolean completeAt) throws ObjectNotFoundException {
        Optional<CheckDetail> byId = checkDetailRepository.findByTodoGroupIdAndId(todoGroupId, id);
        if(!byId.isPresent()) {
            throw new ObjectNotFoundException();
        }

        CheckDetail checkDetailResult = byId.get();
        checkDetailResult.setComplete(completeAt);

        return checkDetailResult;
    }
}
