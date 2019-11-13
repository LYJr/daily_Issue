package com.example.daily_issue.checklist.group.check.service.impl;

import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckDetailRepository extends JpaRepository<CheckDetail, Integer> {
}
