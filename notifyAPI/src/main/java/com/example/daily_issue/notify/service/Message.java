package com.example.daily_issue.notify.service;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Message implements Serializable {
    private Long id;
    private String title;
    private String message;
}
