package com.example.daily_issue.checklist.category.service.impl;

import com.example.daily_issue.checklist.category.service.TodoGroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoGroupCategoryRepository extends JpaRepository<TodoGroupCategory, Integer> {
}
