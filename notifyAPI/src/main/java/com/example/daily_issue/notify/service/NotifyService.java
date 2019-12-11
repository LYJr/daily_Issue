package com.example.daily_issue.notify.service;


import com.example.daily_issue.notify.service.Notify;

public interface NotifyService {
    Notify save(Notify notify);
    void deleteByIdAndListIndex(String key, Long index) ;
    Notify findById(String id);
}
