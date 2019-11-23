package com.example.daily_issue.checklist.category.group.check.service;

import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;

import java.util.List;

public interface CheckDetailService {
    List<CheckDetail> findAll();
    List<CheckDetail> findAllByTodoGroupId(Integer todoGroupId);
    CheckDetail save(Integer todoGroupId,CheckDetail.Request checkDetail) throws ObjectNotFoundException;

    CheckDetail updateByTodoGroupIdAndId(Integer todoGroupId, Integer id, CheckDetail.Request checkDetail) throws ObjectNotFoundException;
    CheckDetail updateCompleteByTodoGroupIdAndId(Integer todoGroupId, Integer id, boolean completeAt) throws ObjectNotFoundException;
}
