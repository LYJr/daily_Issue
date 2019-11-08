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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class RepeatableTask extends AuditableRootTask<Member, Long> implements Cloneable{

    public RepeatableTask(Long id)
    {
        this.setId(id);
    }

    // 반복 단위
    private ChronoUnit repeatChronoUnit;

    // 반복 주기
    private int repeatAmount;

    // 설정일 포함여부
    private boolean isIncludeBaseDate;

    // 반복 시작일
    private LocalDate repeatStartDate;

    // 반복 종료일
    private LocalDate repeatEndDate;

    // 반복 요일 목록
    private String repeatDayOfWeeks;

    // 반복 일 목록
    private String repeatDays;


    public Set<DayOfWeek> getRepeatDayOfWeeks() {
        Set<DayOfWeek> weeks = new HashSet<>();
        for(String dayOfWeek : repeatDayOfWeeks.split(","))
        {
            weeks.add(DayOfWeek.valueOf(dayOfWeek.trim()));
        }
        return weeks;
    }

    public void setRepeatDayOfWeeks(DayOfWeek repeatDayOfWeek) {
        String week = repeatDayOfWeek.toString().trim();
        this.repeatDayOfWeeks = week;
    }

    public void setRepeatDayOfWeeks(Set<DayOfWeek> repeatDayOfWeeks) {
        String weeks = repeatDayOfWeeks.toString();
        weeks = weeks.substring(1, weeks.length() - 1).trim();
        this.repeatDayOfWeeks = weeks;
    }


    public Set<Integer> getRepeatDays() {
        Set<Integer> days = new HashSet<>();
        for(String day : repeatDays.split(","))
        {
            days.add(Integer.valueOf(day.trim()));
        }
        return days;
    }

    public void setRepeatDays(Set<Integer> repeatDays) {
        String days = repeatDays.toString();
        days = days.substring(1, days.length() - 1).trim();
        this.repeatDays = days;
    }



    @Transient
    private boolean isEternalTask = false;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
