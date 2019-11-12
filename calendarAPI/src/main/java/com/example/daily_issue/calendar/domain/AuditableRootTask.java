package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.Member;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Optional;

@EntityListeners(value = { AuditingEntityListener.class, AuditableRootListener.class })
@MappedSuperclass
@Getter @Setter
public class AuditableRootTask<U, PK extends Serializable> extends AbstractPersistable<PK>
        implements Auditable<U, PK, LocalDateTime>  {

    @ManyToOne (fetch = FetchType.LAZY) //
    @JoinColumn(updatable = false, nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private @NonNull U createdBy;

    @Column(updatable = false)
    private @Nullable LocalDateTime createdDate;

    @ManyToOne (fetch = FetchType.LAZY) //
    @JoinColumn(nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private @NonNull U lastModifiedBy;

    private @Nullable LocalDateTime lastModifiedDate;


    @Override
    public Optional<U> getCreatedBy()
    {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public void setCreatedBy(U createdBy)
    {
        this.createdBy = createdBy;
    }

    @Override
    public Optional<LocalDateTime> getCreatedDate() {
        return Optional.ofNullable(this.createdDate);
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Optional<U> getLastModifiedBy()
    {
        return Optional.ofNullable(this.lastModifiedBy);
    }

    @Override
    public void setLastModifiedBy(U lastModifiedBy)
    {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Optional<LocalDateTime> getLastModifiedDate() {
        return Optional.ofNullable(this.lastModifiedDate);
    }

    @Override
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
}
