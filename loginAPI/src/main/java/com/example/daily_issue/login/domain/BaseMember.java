package com.example.daily_issue.login.domain;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseMember {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String userId;

    //password 임시 위치 변경

    public BaseMember(String userId) {
        this.userId = userId;

    }
}
