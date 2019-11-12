package com.example.daily_issue.calendar.dao.repository.basic;

import com.example.daily_issue.calendar.dao.repository.BaseRepository;
import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.login.domain.Member;

import java.util.List;

public interface BasicTaskRepository extends BaseRepository<BasicTaskEntity, Long>, BasicTaskCustomRepository {

    public List<BasicTaskEntity> findByTaskOwner(Member user);
}
