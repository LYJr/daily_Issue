package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class RecordedTask extends AuditableRootTask<Account, Long> {

    public RecordedTask(Long id)
    {
        this.setId(id);
    }



}
