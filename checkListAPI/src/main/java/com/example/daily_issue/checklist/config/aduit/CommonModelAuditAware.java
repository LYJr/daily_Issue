package com.example.daily_issue.checklist.config.aduit;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.login.domain.Member;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CommonModelAuditAware implements AuditorAware<Member> {

    @Override
    public Optional<Member> getCurrentAuditor() {
        return Optional.of(CheckListApplication.account);
    }
}
