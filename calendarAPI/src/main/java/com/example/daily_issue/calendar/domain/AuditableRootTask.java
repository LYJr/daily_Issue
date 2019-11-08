package com.example.daily_issue.calendar.domain;

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
import java.time.*;
import java.util.Date;
import java.util.Optional;

@EntityListeners(value = { AuditingEntityListener.class })
@MappedSuperclass
@Getter @Setter
public class AuditableRootTask<U, PK extends Serializable> extends AbstractPersistable<PK>
        implements Auditable<U, PK, LocalDateTime>  {

    @ManyToOne (fetch = FetchType.LAZY) //
    @JoinColumn(updatable = false, nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private @NonNull U createdBy;

    @Temporal(TemporalType.TIMESTAMP) //
    @Column(updatable = false)
    private @Nullable Date createdDate;

    @ManyToOne (fetch = FetchType.LAZY) //
    @JoinColumn(nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private @NonNull U lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP) //
    private @Nullable Date lastModifiedDate;


    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getCreatedBy()
     */
    @Override
    public Optional<U> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#setCreatedBy(java.lang.Object)
     */
    @Override
    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getCreatedDate()
     */
    @Override
    public Optional<LocalDateTime> getCreatedDate() {
        return null == createdDate ? Optional.empty()
                : Optional.of(LocalDateTime.ofInstant(createdDate.toInstant(), ZoneId.systemDefault()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#setCreatedDate(java.time.temporal.TemporalAccessor)
     */
    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = Date.from(createdDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getLastModifiedBy()
     */
    @Override
    public Optional<U> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#setLastModifiedBy(java.lang.Object)
     */
    @Override
    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getLastModifiedDate()
     */
    @Override
    public Optional<LocalDateTime> getLastModifiedDate() {
        return null == lastModifiedDate ? Optional.empty()
                : Optional.of(LocalDateTime.ofInstant(lastModifiedDate.toInstant(), ZoneId.systemDefault()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#setLastModifiedDate(java.time.temporal.TemporalAccessor)
     */
    @Override
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = Date.from(lastModifiedDate.atZone(ZoneId.systemDefault()).toInstant());
    }






    /* 일정 종일여부
    = 일 단위로 일정관리 여부
    ( true : 시간정보 제외됨) */
    private Boolean isAllDay = false;

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
