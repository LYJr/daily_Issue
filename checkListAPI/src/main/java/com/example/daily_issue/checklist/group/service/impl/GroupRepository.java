package com.example.daily_issue.checklist.group.service.impl;

import com.example.daily_issue.checklist.group.service.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
