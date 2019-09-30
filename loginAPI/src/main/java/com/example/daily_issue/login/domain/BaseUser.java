package com.example.daily_issue.login.domain;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String userId;

    @Column
    private String password;

    public BaseUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
