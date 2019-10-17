package com.example.daily_issue.checklist.group.service;

import com.example.daily_issue.checklist.common.service.CommonModel;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity @Table(name="TBN_GROUP")
@Getter @Setter @Builder @ToString @EqualsAndHashCode(of = "groupId")
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Group extends CommonModel{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer groupId;
    private String title;
    private String contents;
}
