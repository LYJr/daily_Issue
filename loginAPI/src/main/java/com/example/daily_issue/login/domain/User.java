package com.example.daily_issue.login.domain;

import com.example.daily_issue.login.dto.UserDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Getter
public class User extends BaseUser {

    @Column
    private String name;

    @Column
    private String status;

    @Column
    private String birthday;

    @Column
    private String phoneNumber;

    public User(String id, String pw, String s, String status, String birthday, String phoneNumber) {
        super(id, pw);
        this.name = s;
        this.status = status;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public boolean idConfirm(String userId) {
        return super.getUserId().equals(userId);
    }

    public boolean passwordConfirm (String password) {
        return super.getPassword().equals(password);
    }

    public boolean overlapConfirm (UserDto userDto) {
        return super.getUserId().equals(userDto.getUserId()) && this.phoneNumber.equals(userDto.getPhoneNumber());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "userId='" + getUserId() + '\'' +
                "password= " + getPassword() + '\'' +
                "name='" + name + '\'' +
                "status='" + status + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
