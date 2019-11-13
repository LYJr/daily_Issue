package com.example.daily_issue.checklist.group.check.service;

import java.util.List;

public interface CheckDetailService {
    List<CheckDetail> findAll();

    CheckDetail save(CheckDetail checkDetail);
}
