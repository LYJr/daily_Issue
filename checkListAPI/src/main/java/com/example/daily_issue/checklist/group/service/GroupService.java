package com.example.daily_issue.checklist.group.service;

import java.util.Optional;

public interface GroupService {
    public Optional<Group> findById(Integer id);
    public Group save(Group group);
}
