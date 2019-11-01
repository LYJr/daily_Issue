package com.example.daily_issue.checklist.config.aduit;

import com.example.daily_issue.checklist.user.service.User;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CommonModelAuditAware implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.of(User.builder().id("kyoing").build());
    }
}
