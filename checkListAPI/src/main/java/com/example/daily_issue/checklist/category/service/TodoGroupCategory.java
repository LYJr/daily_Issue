package com.example.daily_issue.checklist.category.service;

import com.example.daily_issue.checklist.category.group.service.TodoGroup;
import com.example.daily_issue.checklist.common.service.CommonModel;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.ui.ModelMap;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString(of="id", callSuper = true) @EqualsAndHashCode(of = "id", callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class TodoGroupCategory extends CommonModel {
    private String name;

    @OneToMany(mappedBy = "todoGroupCategory")
    private List<TodoGroup> todoGroupList;

    @Getter @Setter @NoArgsConstructor
    public static class Request {
        private Integer id;
        private String name;

        public TodoGroupCategory converToOriginal() {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(this, TodoGroupCategory.class);
        }
    }
    @Getter @Setter @NoArgsConstructor
    public static class Response {
        private Integer id;
        private String name;
    }

    public Response converToResponse() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this,Response.class);
    }
}
