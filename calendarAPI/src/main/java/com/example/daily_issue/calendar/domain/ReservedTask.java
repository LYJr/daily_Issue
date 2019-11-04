package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ReservedTask extends AuditableRootTask<Account, Long> {

    public ReservedTask(Long id)
    {
        this.setId(id);
    }

    // 예약 발생 주기 (기본 : 한달단위)
    private Period period = Period.ofMonths(1);

    // 예약 시작일
    private LocalDateTime reserveStartDate;
    // 예약 종료일
    private LocalDateTime reserveEndDate;


    @Transient
    private boolean isEternalTask = false;

//    @Transient
//    private boolean isEnable = true;

}
