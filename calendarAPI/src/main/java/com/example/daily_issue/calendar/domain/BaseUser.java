package com.example.daily_issue.calendar.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String userId;

    @Column
    private String password;

    public BaseUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
