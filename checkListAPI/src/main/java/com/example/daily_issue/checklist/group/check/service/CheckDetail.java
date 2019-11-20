package com.example.daily_issue.checklist.group.check.service;

import com.example.daily_issue.checklist.common.service.CommonModel;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Check;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.ui.ModelMap;

import javax.persistence.*;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@ToString(of="id", callSuper = true) @EqualsAndHashCode(of = "id", callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class CheckDetail extends CommonModel {
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
    private TodoGroup todoGroup;
    private String title;
    private String contents;
    private boolean complete;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Integer id;
        private String title;
        private String contents;
        private boolean complete;
        @JsonBackReference
        private TodoGroup.Response todoGroup;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        private Integer id;
        private Integer todoGroupId;
        private String title;
        private String contents;
        private boolean complete;

        public CheckDetail converToOriginal() {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(this, CheckDetail.class);
        }
    }

    public Response convertToResponse() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PROTECTED);
        modelMapper.getConfiguration().setMethodAccessLevel(Configuration.AccessLevel.PROTECTED);
        for(int i = 0; i<100; i++) System.out.println(this.getId());
        return modelMapper.map(this, Response.class);
    }
}
