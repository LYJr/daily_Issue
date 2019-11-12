package com.example.daily_issue.calendar.attr;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum ServiceURLAttributes {

    CONTEXT("/calendar", HttpMethod.GET),
        INDEX(CONTEXT.getUrl() + "/index", HttpMethod.GET),
        TASK_CREATE(CONTEXT.getUrl() + "/save", HttpMethod.POST),
        TASK_MODIFY(CONTEXT.getUrl() + "/update", HttpMethod.PUT),
        TASK_DELETE(CONTEXT.getUrl() + "/delete", HttpMethod.DELETE),

    LOGIN("/login", HttpMethod.GET),
    LOGOUT("/logout", HttpMethod.GET)
    ;



    final private String url;
    final private HttpMethod method;
    private ServiceURLAttributes(String url, HttpMethod method)
    {
        this.url = url;
        this.method = method;
    }
}
