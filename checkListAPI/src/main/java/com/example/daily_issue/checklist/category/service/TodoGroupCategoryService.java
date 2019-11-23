package com.example.daily_issue.checklist.category.service;

import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;

import java.util.List;

public interface TodoGroupCategoryService {
    public TodoGroupCategory save(TodoGroupCategory todoGroupCategory);

    TodoGroupCategory findById(int id) throws ObjectNotFoundException;

    List<TodoGroupCategory> findAll();

    TodoGroupCategory updateById(int id,TodoGroupCategory.Request todoGroupCategory) throws ObjectNotFoundException;
}
