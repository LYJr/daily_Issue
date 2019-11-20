package com.example.daily_issue.checklist.group.check.service.impl;

import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckDetailRepository extends JpaRepository<CheckDetail, Integer> {
    List<CheckDetail> findAllByTodoGroupId(Integer todoGroupId);

    Optional<CheckDetail> findByTodoGroupIdAndId(Integer todoGroupId, Integer id);
}
