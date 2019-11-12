package com.example.daily_issue.calendar.security.aop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface EnableOwnerCheck {
}
