package com.example.daily_issue.calendar.dao.impl.basic;

import com.example.daily_issue.calendar.dao.BaseRepository;
import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.login.domain.Member;

import java.util.List;

public interface BasicTaskRepository extends BaseRepository<BasicTask, Long>, BasicTaskCustomRepository {

    public List<BasicTask> findByTaskOwner(Member user);
}
