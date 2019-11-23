package com.example.daily_issue.checklist.category.service.impl;

import com.example.daily_issue.checklist.category.service.TodoGroupCategory;
import com.example.daily_issue.checklist.category.service.TodoGroupCategoryService;
import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoGroupCategoryServiceImpl implements TodoGroupCategoryService {
    private TodoGroupCategoryRepository todoGroupCategoryRepository;

    public TodoGroupCategoryServiceImpl(TodoGroupCategoryRepository todoGroupCategoryRepository) {
        this.todoGroupCategoryRepository = todoGroupCategoryRepository;
    }
    @Override
    public TodoGroupCategory save(TodoGroupCategory todoGroupCategory) {
        todoGroupCategoryRepository.save(todoGroupCategory);
        return todoGroupCategory;
    }
    @Override
    public TodoGroupCategory findById(int id) throws ObjectNotFoundException {
        Optional<TodoGroupCategory> byId = todoGroupCategoryRepository.findById(id);

        if(!byId.isPresent()) {
            throw new ObjectNotFoundException();
        }

        return byId.get();
    }
    @Override
    public List<TodoGroupCategory> findAll() {
        return todoGroupCategoryRepository.findAll();
    }
    @Override
    public TodoGroupCategory updateById(int id,TodoGroupCategory.Request todoGroupCategory) throws ObjectNotFoundException {
        TodoGroupCategory result = findById(id);

        result.setName(todoGroupCategory.getName());
        return result;
    }
    public void deleteById(int id) {
        todoGroupCategoryRepository.deleteById(id);
    }
}
