package com.example.daily_issue.checklist.group.check.service;

import com.example.daily_issue.checklist.common.service.CommonModel;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class CheckDetail extends CommonModel {
    private String title;
    private String contents;
    private boolean complete;
}
