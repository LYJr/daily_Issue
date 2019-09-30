package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.User;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = { AuditingEntityListener.class })
@MappedSuperclass
public class RootTask extends AbstractAuditable<User, Long> {

}
