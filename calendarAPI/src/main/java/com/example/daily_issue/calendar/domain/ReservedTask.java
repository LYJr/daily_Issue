package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.Period;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ReservedTask extends AuditableTask<Account, Long> {

    public ReservedTask(Long id)
    {
        this.setId(id);
    }

    // 예약 대상 일정
    @OneToOne(mappedBy = "reservedTask", fetch = FetchType.LAZY)
    private RecordedTask recordedTask;

    // 예약 발생 주기 (기본 : 한달단위)
    private Period period = Period.ofMonths(1);

    //
}
