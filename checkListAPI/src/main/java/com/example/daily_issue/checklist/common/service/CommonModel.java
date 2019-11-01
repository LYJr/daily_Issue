package com.example.daily_issue.checklist.common.service;

import com.example.daily_issue.checklist.user.service.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Embeddable
@MappedSuperclass
public class CommonModel extends AbstractAuditable<User, Integer> {
    private Character deleteAt = 'N';       // 삭제여부
}
