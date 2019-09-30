package com.example.daily_issue.login.dto;

import com.example.daily_issue.login.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String userId;
    private String password;
    private String name;
    private String status;
    private String birthday;
    private String phoneNumber;

    public UserDto(String userId, String password, String name, String status, String birthday, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.status = status;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public User toUser(){
        return new User(this.userId, this.password, this.name, this.status, this.birthday, this.phoneNumber);
    }

}
