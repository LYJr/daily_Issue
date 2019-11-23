package com.example.daily_issue.checklist.category.group.service;

import com.example.daily_issue.checklist.category.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.category.service.TodoGroupCategory;
import com.example.daily_issue.checklist.common.service.CommonModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @Builder
@ToString(of="id", callSuper = true) @EqualsAndHashCode(of = "id", callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class TodoGroup extends CommonModel {
    @ManyToOne
    private TodoGroupCategory todoGroupCategory;
    private String title;           // 제목
    private String contents;        // 내용

    @OneToMany(mappedBy = "todoGroup", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<CheckDetail> checkDetails = new ArrayList<>();


    public TodoGroup.Response convertToResponse() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<TodoGroup, Response> responseTypeMap = modelMapper.typeMap(TodoGroup.class, Response.class)
                .addMappings(
                        item -> {
                            item.skip(Response::setCheckDetails);
                        }
                );
        return responseTypeMap.map(this);
    }

    public TodoGroup.Request convertToRequest() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<TodoGroup, Request> responseTypeMap = modelMapper.typeMap(TodoGroup.class, Request.class)
                .addMappings(
                        item -> {
                            item.skip(Request::setCheckDetails);
                        }
                );
        return responseTypeMap.map(this);
    }

    public void addCheckDetail(CheckDetail checkDetail) {
        checkDetails.add(checkDetail);
        checkDetail.setTodoGroup(this);
    }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        private Integer todoGroupCategoryId;

        private Integer id;
        private String title;
        private String contents;
        private List<CheckDetail.Request> checkDetails = new ArrayList<>();

        public TodoGroup convertToOriginal() {
            ModelMapper modelMapper = new ModelMapper();

            modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PROTECTED);
            modelMapper.getConfiguration().setMethodAccessLevel(Configuration.AccessLevel.PROTECTED);

            TypeMap<Request, TodoGroup> requestTodoGroupTypeMap = modelMapper.typeMap(Request.class, TodoGroup.class).
                    addMappings(item -> item.skip(TodoGroup::setCheckDetails));

            TodoGroup todoGroup = requestTodoGroupTypeMap.map(this);
            todoGroup = addCheckDetails(todoGroup);

            return todoGroup;
        }
        public TodoGroup addCheckDetails(TodoGroup todoGroup) {
            if(checkDetails != null) {
                for(CheckDetail.Request checkDetail : checkDetails) {
                    todoGroup.addCheckDetail(checkDetail.converToOriginal());
                }
            }

            return todoGroup;
        }
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Integer id;
        private String title;
        private String contents;
        @JsonManagedReference
        private List<CheckDetail.Response> checkDetails = new ArrayList<>();
    }
}
