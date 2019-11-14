package com.example.daily_issue.login.domain;

import com.example.daily_issue.login.dto.MemberDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Getter
public class Member extends BaseMember {

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String status;

    @Column
    private String birthday;

    @Column
    private String phoneNumber;

    public Member(String id, String password, String name, String status, String birthday, String phoneNumber) {
        super(id);
        this.password = password;
        this.name = name;
        this.status = status;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public boolean idConfirm(String userId) {
        return super.getUserId().equals(userId);
    }

    public boolean passwordConfirm (String password) {
        return this.password.equals(password);
    }

    public boolean overlapConfirm (MemberDto memberDto) {
        return super.getUserId().equals(memberDto.getUserId()) && this.phoneNumber.equals(memberDto.getPhoneNumber());
    }

    @Override
    public String toString() {
        return "User{" +
                "password=" + this.password + '\'' +
                "id='" + getId() + '\'' +
                "userId='" + getUserId() + '\'' +
                "password= " + getPassword() + '\'' +
                "name='" + name + '\'' +
                "status='" + status + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
