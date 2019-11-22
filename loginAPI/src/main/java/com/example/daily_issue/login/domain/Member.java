package com.example.daily_issue.login.domain;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Getter
public class Member extends BaseMember {

    @Column
    private String name;

    @Column
    private String role = "OK";

    @Column
    private String birthday;

    @Column(unique = true)
    private String phoneNumber;

    public Member(String memberId, String password) {
        super(memberId, password);
    }

    public Member(String memberId, String password, String name, String birthday, String phoneNumber) {
        super(memberId, password);
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "password=" + getPassword() + '\'' +
                "id='" + getId() + '\'' +
                "userId='" + getUserId() + '\'' +
                "password= " + getPassword() + '\'' +
                "name='" + name + '\'' +
                "status='" + role + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
