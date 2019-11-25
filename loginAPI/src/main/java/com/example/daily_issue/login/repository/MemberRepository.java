package com.example.daily_issue.login.repository;

import com.example.daily_issue.login.domain.DailyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<DailyMember, Long> {
    Optional<DailyMember> findByUserId(String userId);
}
