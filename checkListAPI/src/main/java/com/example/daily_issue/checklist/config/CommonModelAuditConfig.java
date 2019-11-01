package com.example.daily_issue.checklist.config;

import com.example.daily_issue.checklist.user.service.User;
import com.example.daily_issue.checklist.config.aduit.CommonModelAuditAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Configuration
public class CommonModelAuditConfig {

    @Bean
    AuditorAware<User> auditorProvider() {
        return new CommonModelAuditAware();
    }
}
