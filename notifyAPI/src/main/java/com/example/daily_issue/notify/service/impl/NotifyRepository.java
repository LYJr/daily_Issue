package com.example.daily_issue.notify.service.impl;

import com.example.daily_issue.notify.service.Notify;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyRepository extends CrudRepository<Notify, String> {
}
