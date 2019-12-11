package com.example.daily_issue.notify.service.impl;

import com.example.daily_issue.notify.service.Notify;
import com.example.daily_issue.notify.service.NotifyRepository;
import com.example.daily_issue.notify.service.NotifyService;
import io.lettuce.core.api.sync.RedisHashCommands;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NotifyServiceImpl implements NotifyService {
    private NotifyRepository notifyRepository;
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Notify save(Notify notify) {
        return notifyRepository.save(notify);
    }

    @Override
    @Transactional
    public void deleteByIdAndListIndex(String key, Long index) {
        notifyRepository.deleteByIdAndListIndex(key, index);
    }

    @Override
    public Notify findById(String id) {
        return notifyRepository.findById(id);
    }
}
