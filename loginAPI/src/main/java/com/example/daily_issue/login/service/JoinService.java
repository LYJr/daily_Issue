package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.Member;
import com.example.daily_issue.login.repository.MemberRepository;
import com.example.daily_issue.login.dto.MemberDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Getter
@Service
public class JoinService {

    @Autowired
    private MemberRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member add(Member member){
        return userRepository.save(member);
    }

    /**
     * 아이디, 핸드폰 번호 확인 메서드
     * @param memberDto
     * @return
     * @throws IllegalArgumentException
     */
    public Member userConfirm (MemberDto memberDto) {
        return memberDto.toMember();
    }

    private String conversion (String input) {
        return (input);
    }

   public Member createNew (Member member) {
        member.encodePassword(passwordEncoder);
        return add(member);
   }

   public String TestPassword(Member member) {
        member.encodePassword(passwordEncoder);
        return member.getPassword();
   }
}
