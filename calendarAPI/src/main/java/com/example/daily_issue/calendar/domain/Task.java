package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Task extends RootTask<Account, Long> {

    public Task(Long id)
    {
        this.setId(id);
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

    /* 일정 시작일시 */
    @Column(nullable = false)
    private LocalDateTime startDateTime = LocalDateTime.now();

    /* 일정 종료일시 */
    @Column(nullable = false)
    private LocalDateTime endDateTime = LocalDateTime.now();

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
