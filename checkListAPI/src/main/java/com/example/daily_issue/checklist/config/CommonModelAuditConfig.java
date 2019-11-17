package com.example.daily_issue.checklist.config;

import com.example.daily_issue.checklist.config.aduit.CommonModelAuditAware;
import com.example.daily_issue.login.domain.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Configuration
public class CommonModelAuditConfig {

    @Bean
    AuditorAware<Member> auditorProvider() {
        return new CommonModelAuditAware();
    }
}
