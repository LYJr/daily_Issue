package com.example.daily_issue.calendar.dao.impl.member;

import com.example.daily_issue.calendar.dao.BaseRepository;
import com.example.daily_issue.login.domain.Member;

public interface MemberRepository extends BaseRepository<Member, Long> {

    public Member findByUserId(String userId);
}
