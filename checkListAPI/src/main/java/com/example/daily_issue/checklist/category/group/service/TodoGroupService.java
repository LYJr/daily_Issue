package com.example.daily_issue.checklist.category.group.service;

import java.util.Optional;

public interface TodoGroupService {
    Optional<TodoGroup> findById(Integer id);
    TodoGroup save(TodoGroup todoGroup);
    void deleteById(Integer id);
    TodoGroup update(TodoGroup todoGroup);
}
