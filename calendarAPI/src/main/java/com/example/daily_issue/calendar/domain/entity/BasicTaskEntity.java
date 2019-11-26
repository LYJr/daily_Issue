package com.example.daily_issue.calendar.domain.entity;

import com.example.daily_issue.login.domain.Member;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class BasicTaskEntity extends AuditableRootEntity<Member, Long> {

    public BasicTaskEntity(Long id)
    {
        this.setId(id);
    }


    /* task 생성 사용자 */
    @ManyToOne (fetch = FetchType.LAZY) //
    @JoinColumn(updatable = true, nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Member taskOwner;



    /* 일정 종일여부
    = 일 단위로 일정관리 여부
    ( true : 시간정보 제외됨) */
    private Boolean isAllDay = false;

    /* 반복여부 */
    private boolean isRepeatable = false;

    /* 일정 시작일 */
    @Column(nullable = false)
    private LocalDate taskStartDate = LocalDate.now();
    /* 일정 시작시 */
    @Column(nullable = false)
    private LocalTime taskStartTime = LocalTime.now();

    /* 일정 종료일 */
    @Column(nullable = false)
    private LocalDate taskEndDate = LocalDate.now();
    /* 일정 종료시 */
    @Column(nullable = false)
    private LocalTime taskEndTime = LocalTime.now();

    /* 일정 제목 */
    @Column(nullable = false)
    private String title = "";

    /* 일정 상세설명 */
    private String comment = "";

    /* 일정 위치 / 장소 */
    private String place = "";

    /* 일정 표기 색 (default : black) */
    /*
     * rgb : rgb(0, 0, 0)
     * cmyk : cmyk(0%, 0%, 0%, 100%)
     * hsl : hsl(0, 0%, 0%)
     * hex : #000000
     * */
    private String color = "rgb(0, 0, 0)";


    @Transient
    /* 일정 시작 / 종료일 기간 */
    public Period getTaskPeriod()
    {
        return Period.between(taskStartDate, taskEndDate);
    }


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "basicTask")
    private Set<RepeatableTaskEntity> repeatableTasks;

    public void setRepeatableTasks(Set<RepeatableTaskEntity> repeatableTasks) {
        repeatableTasks.forEach(task -> task.setBasicTask(this));
        this.repeatableTasks = repeatableTasks;
    }
}
