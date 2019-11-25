package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.DailyMember;
import com.example.daily_issue.login.domain.MemberPrincipal;
import com.example.daily_issue.login.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberService() {
        super();
    }

    @PostConstruct
    public void completeSetup() {
        memberRepository = applicationContext.getBean(MemberRepository.class);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<DailyMember> originMember = findByUserId(userName);

        if (!originMember.isPresent()) {
            throw new UsernameNotFoundException("Not User");
        }

        DailyMember member = originMember.get();
        return new MemberPrincipal(member);
    }

    public List<DailyMember> findAll() {
        return memberRepository.findAll();
    }

    public Optional<DailyMember> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }


    public DailyMember add(DailyMember member) {
        return memberRepository.save(member);
    }

    public DailyMember createNew(DailyMember member) {
        member.encodePassword(passwordEncoder);
        return add(member);
    }

//    public boolean login(MemberDto memberDto) {
//        Optional<Member> originUser = findByUserId(memberDto.getUserId());
//
//        if (originUser.isPresent()) {
//            Member user = originUser.get();
//            if(user.passwordConfirm(memberDto.getPassword())){
//                return true;
//            }
//        }
//        return false;
//    }

}
