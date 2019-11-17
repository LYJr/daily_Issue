package com.example.daily_issue.checklist.group.service;

import com.example.daily_issue.checklist.common.service.CommonModel;
import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @Builder
@ToString(of="id", callSuper = true) @EqualsAndHashCode(of = "id", callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class TodoGroup extends CommonModel {
    private String title;           // 제목
    private String contents;        // 내용
    @OneToMany(mappedBy = "todoGroup")
    private List<CheckDetail> checkDetails = new ArrayList<>();

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        private Integer id;
        private String title;
        private String contents;
        private List<CheckDetail.Request> checkDetails = new ArrayList<>();
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Integer id;
        private String title;
        private String contents;
        private List<CheckDetail.Response> checkDetails = new ArrayList<>();
    }

    public void addCheckDetail(CheckDetail checkDetail) {
        this.checkDetails.add(checkDetail);
        checkDetail.setTodoGroup(this);
    }
}
