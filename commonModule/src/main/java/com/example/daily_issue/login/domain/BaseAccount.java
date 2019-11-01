package com.example.daily_issue.login.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@NoArgsConstructor
public class BaseAccount {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column
    private String userId;

    @Setter
    @Column
    private String password;

    public BaseAccount(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
