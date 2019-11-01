package com.example.daily_issue.calendar.security.service;

import com.example.daily_issue.calendar.config.ApplicationConsts;
import com.example.daily_issue.calendar.dao.AccountRepository;
import com.example.daily_issue.login.domain.Account;
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
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByUserId(username);
        if(account == null)
        {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(account.getUserId())
                .password(passwordEncoder.encode(account.getPassword()))
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


    public Account getAccount()
    {
        return (Account) session.getAttribute(ApplicationConsts.ACCOUNT_SESS_NAME);
    }


    public Account setAccount(String userid)
    {
        Account account = getAccount();

        // TODO: 2019-11-01 차후에 orElseGet은 빠져야한다... 인증 후 session에 넣고, repo 건드리고는.. loginAPI에서 해야한다.
        return Optional.ofNullable(account).orElseGet(() -> {
                    String tempUserid = Optional.ofNullable(userid).orElseGet(() -> getPrincipal().get().getUsername());
                    Account tempAcc = accountRepository.findByUserId(tempUserid);
                    session.setAttribute(ApplicationConsts.ACCOUNT_SESS_NAME, tempAcc);
                    return tempAcc;
                }
            );
    }
}
