package com.example.daily_issue.checklist.member.service.impl;

import com.example.daily_issue.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByUserId(String userId);
}
