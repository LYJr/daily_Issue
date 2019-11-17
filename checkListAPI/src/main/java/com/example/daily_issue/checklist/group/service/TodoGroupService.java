package com.example.daily_issue.checklist.group.service;

import java.util.Optional;

public interface TodoGroupService {
    Optional<TodoGroup> findById(Integer id);
    TodoGroup save(TodoGroup.Request todoGroup);
    void deleteById(Integer id);
    TodoGroup update(TodoGroup.Request todoRequest);
}
