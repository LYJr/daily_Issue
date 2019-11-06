package com.example.daily_issue.checklist.config.aduit;

import com.example.daily_issue.login.domain.Account;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CommonModelAuditAware implements AuditorAware<Account> {

    @Override
    public Optional<Account> getCurrentAuditor() {
        Account account = new Account();
        account.setUserId("kyoing");
        return Optional.of(account);
    }
}
