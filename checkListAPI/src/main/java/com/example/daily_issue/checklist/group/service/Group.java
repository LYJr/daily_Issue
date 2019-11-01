package com.example.daily_issue.checklist.group.service;

import com.example.daily_issue.checklist.common.service.CommonModel;
import com.example.daily_issue.checklist.user.service.User;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity @Table(name="TBN_GROUP")
@Getter @Setter @Builder @ToString @EqualsAndHashCode(of = "id", callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class Group extends CommonModel {
    private String title;           // 제목
    private String contents;        // 내용
}
