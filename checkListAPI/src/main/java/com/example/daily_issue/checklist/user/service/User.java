package com.example.daily_issue.checklist.user.service;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class User {
    @Id
    private String id;
}
