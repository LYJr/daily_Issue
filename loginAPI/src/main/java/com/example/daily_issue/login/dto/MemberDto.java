package com.example.daily_issue.login.dto;

import com.example.daily_issue.login.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

    private String userId;
    private String password;
    private String name;
    private String status;
    private String birthday;
    private String phoneNumber;

    public MemberDto(String userId, String password, String name, String status, String birthday, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.status = status;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public Member toMember(){
        return new Member(this.userId, this.password, this.name, this.status, this.birthday, this.phoneNumber);
    }

}
