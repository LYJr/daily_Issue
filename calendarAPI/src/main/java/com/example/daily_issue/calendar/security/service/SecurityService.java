package com.example.daily_issue.calendar.security.service;

import com.example.daily_issue.calendar.config.ApplicationConsts;
import com.example.daily_issue.calendar.dao.impl.member.MemberRepository;
import com.example.daily_issue.login.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpSession session;

    @Autowired
    MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUserId(username);
        if(member == null)
        {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(member.getUserId())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles("USER")
                .build();
    }


    public Optional<UserDetails> getPrincipal()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
        {
            return Optional.of((UserDetails) principal);
        }
        return Optional.empty();
    }


    public Member getMember()
    {
        Object member = session.getAttribute(ApplicationConsts.MEMBER_SESS_NAME);
        if(member == null || !(member instanceof  Member))
        {
            SecurityContextHolder.clearContext();
        }

        return (Member) member;
    }


    public Member setMember(String userid)
    {
        Member member = getMember();

        member = Optional.ofNullable(member).orElseGet(() -> {
                    String tempUserid = Optional.ofNullable(userid).orElseGet(() -> getPrincipal().get().getUsername());
                    Member tempAcc = memberRepository.findByUserId(tempUserid);
                    session.setAttribute(ApplicationConsts.MEMBER_SESS_NAME, tempAcc);
                    return tempAcc;
                }
        );

        System.out.println(getMember());

        // TODO: 2019-11-01 차후에 orElseGet은 빠져야한다... 인증 후 session에 넣고, repo 건드리고는.. loginAPI에서 해야한다.
        return member;
    }
}
