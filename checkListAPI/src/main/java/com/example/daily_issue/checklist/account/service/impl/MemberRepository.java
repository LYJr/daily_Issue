package com.example.daily_issue.checklist.account.service.impl;

import com.example.daily_issue.calendar.dao.repository.BaseRepository;
import com.example.daily_issue.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends BaseRepository<Member, Long> {

    public Member findByUserId(String userId);
}
