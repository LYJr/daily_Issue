package com.example.daily_issue.checklist.category.group.service.impl;

import com.example.daily_issue.checklist.category.group.service.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoGroupRepository extends JpaRepository<TodoGroup, Integer> {
}
