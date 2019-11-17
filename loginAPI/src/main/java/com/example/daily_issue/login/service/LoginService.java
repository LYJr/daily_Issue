package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.Member;
import com.example.daily_issue.login.repository.MemberRepository;
import com.example.daily_issue.login.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private MemberRepository userRepository;

    public Optional<Member> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean login(MemberDto memberDto) {
        Optional<Member> originUser = findByUserId(memberDto.getUserId());

        if (originUser.isPresent()) {
            Member user = originUser.get();
            if(user.passwordConfirm(memberDto.getPassword())){
                return true;
            }
        }
        return false;
    }

}
