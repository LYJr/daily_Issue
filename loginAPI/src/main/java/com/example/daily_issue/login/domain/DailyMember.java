package com.example.daily_issue.login.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Getter
@NoArgsConstructor
public class DailyMember extends BaseMember {

    @Column
    private String userName;

    @Column
    private String role = "OK";

    @Column
    private String birthday;

    @Column(unique = true)
    private String phoneNumber;

    public DailyMember(String userId, String password) {
        super(userId, password);
    }

    public DailyMember(String userId, String password, String name, String birthday, String phoneNumber) {
        super(userId, password);
        this.userName = name;
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
                "name='" + userName + '\'' +
                "status='" + role + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
