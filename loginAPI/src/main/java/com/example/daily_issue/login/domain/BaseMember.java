package com.example.daily_issue.login.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseMember {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column
    private String password;

    public BaseMember(String memberId, String password) {
        this.userId = memberId;
        this.password = password;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
