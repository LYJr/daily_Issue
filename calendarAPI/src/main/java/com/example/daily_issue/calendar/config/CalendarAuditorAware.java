package com.example.daily_issue.calendar.config;

import com.example.daily_issue.calendar.security.service.SecurityService;
import com.example.daily_issue.login.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnMissingBean(AuditorAware.class)
public class CalendarAuditorAware implements AuditorAware<Member> {

    @Autowired
    SecurityService securityService;

    @Override
    public Optional<Member> getCurrentAuditor() {

        Member member = securityService.getMember();

        return Optional.ofNullable(member);
    }
}
