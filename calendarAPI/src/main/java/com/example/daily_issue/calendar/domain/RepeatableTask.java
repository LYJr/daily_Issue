package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class RepeatableTask extends AuditableRootTask<Member, Long> {

    public RepeatableTask(Long id)
    {
        this.setId(id);
    }

    // 반복 발생 주기
    private Period repeatPeriod;

    // 반복 시작일
    private LocalDate repeatStartDate;

    // 반복 종료일
    private LocalDate repeatEndDate;


    @Transient
    private boolean isEternalTask = false;

//    @Transient
//    private boolean isEnable = true;

}
