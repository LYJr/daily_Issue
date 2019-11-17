package com.example.daily_issue.checklist.group.check.service;

import java.util.List;

public interface CheckDetailService {
    List<CheckDetail> findAll();
    List<CheckDetail> findAllByTodoGroupId(Integer todoGroupId);
    CheckDetail save(CheckDetail.Request request);
}
