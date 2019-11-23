package com.example.daily_issue.checklist.category.group.check.service;

import com.example.daily_issue.checklist.common.service.CommonModel;
import com.example.daily_issue.checklist.category.group.service.TodoGroup;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@ToString(of="id", callSuper = true) @EqualsAndHashCode(of = "id", callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class CheckDetail extends CommonModel {
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
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
            modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PROTECTED);
            modelMapper.getConfiguration().setMethodAccessLevel(Configuration.AccessLevel.PROTECTED);
            return modelMapper.map(this, CheckDetail.class);
        }
    }

    public Response convertToResponse() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PROTECTED);
        modelMapper.getConfiguration().setMethodAccessLevel(Configuration.AccessLevel.PROTECTED);
        return modelMapper.map(this, Response.class);
    }
}
