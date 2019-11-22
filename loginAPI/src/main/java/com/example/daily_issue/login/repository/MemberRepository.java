package com.example.daily_issue.login.repository;

import com.example.daily_issue.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String userId);
//    Optional<Member> findByUserId(String userId);
}
