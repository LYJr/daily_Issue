package com.example.daily_issue.notify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Notify implements Serializable {
    public static final String redisHash = "notify";
    private String userId;
    private List<Message> messages = new ArrayList<>();

    public String getKey() {
        return redisHash + ":" + getUserId();
    }
}
