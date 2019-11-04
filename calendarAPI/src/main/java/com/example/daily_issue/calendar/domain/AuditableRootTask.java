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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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













    // TODO: 2019-10-02 Calendar 추가기능 모색
    /*
     * 1. google calendar를 기준으로 작성
     * 2. 추가 기능은 아래와 같다.
     *   1. 일정반복 / 참석자 / 참석자별 권한
     *   2. 일정 공개여부 / 공개 URL
     *   3. 일정초대 / 초대 URL
     *   4. 일정 알림 (email / mobile / etc..)
     * 3. 추가기능은 현재 목적과 벗어나므로, 필요 시 구현
     * */


    /* 일정 수행자 : 생성자와 일정 담당자가 다를 수 있으므로..
     * 일단은 사용하지 않는다. */
    /*@ManyToOne
    @JoinColumn(nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Account taskPerformer;*/



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
}
