package com.example.daily_issue.calendar.dao;

import com.example.daily_issue.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByUserId(String userId);
}
