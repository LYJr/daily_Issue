package com.example.daily_issue.calendar.config;

import com.example.daily_issue.login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class CalendarAuditorAware implements AuditorAware<User> {

    @Autowired
    HttpSession session;

    @Override
    public Optional<User> getCurrentAuditor() {

        // 사용자 id값을 가져와서 session이든 spring security든 어떻게든 가져올 수 있다고 가정
        Optional<User> user = (Optional<User>) session.getAttribute("UserSess");
        return user;
    }
}
