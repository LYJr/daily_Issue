package com.example.daily_issue.checklist.group.check.service;

import com.example.daily_issue.checklist.common.service.CommonModel;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class CheckDetail extends CommonModel {
    @ManyToOne
    private TodoGroup todoGroup;
    private String title;
    private String contents;
    private boolean complete;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private String title;
        private String contents;
        private boolean complete;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        private String title;
        private String contents;
        private boolean complete;
    }
}
