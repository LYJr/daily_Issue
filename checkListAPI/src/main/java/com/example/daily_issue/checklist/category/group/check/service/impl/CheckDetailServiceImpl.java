package com.example.daily_issue.checklist.category.group.check.service.impl;

import com.example.daily_issue.checklist.category.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.category.group.check.service.CheckDetailService;
import com.example.daily_issue.checklist.category.group.service.impl.TodoGroupRepository;
import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;
import com.example.daily_issue.checklist.category.group.service.TodoGroup;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CheckDetail save(Integer todoGroupId,CheckDetail.Request checkDetail) throws ObjectNotFoundException {
        Optional<TodoGroup> todoGroupOptional = todoGroupRepository.findById(todoGroupId);
        if(!todoGroupOptional.isPresent()) {
            throw new ObjectNotFoundException();
        }

        CheckDetail detailResult = checkDetail.converToOriginal();
        for(int i = 0; i<100; i++) {
            System.out.println(detailResult.getTodoGroup().getId());
        }
        checkDetailRepository.save(detailResult);

        return detailResult;
    }

    @Override
    public CheckDetail updateByTodoGroupIdAndId(Integer todoGroupId, Integer id, CheckDetail.Request checkDetail) throws ObjectNotFoundException {
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
    @Transactional
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
