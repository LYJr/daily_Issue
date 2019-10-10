package com.example.daily_issue.checklist.common.service;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Embeddable
@MappedSuperclass
public class CommonModel {
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdDate;               // 생성일
    @Column
    private String creatorUserId;           // 생성자 아이디
    @LastModifiedDate
    @Column
    private Date lastModifiedDate;          // 마지막 수정일
    @Column
    private String lastModifierUserId;      // 마지막 수정자 아이디
    @Column
    private Character deleteAt = 'N';       // 삭제여부
}
