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
    public boolean deleteById(String id) {
        return notifyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Long deleteByIdAndListIndex(String key, Long index) {
        return notifyRepository.deleteByIdAndListIndex(key, index);
    }

    @Override
    public Notify findById(String id) {
        return notifyRepository.findById(id);
    }

    @Override
    public Notify findByIdAndIndex(String id, Long index) {
        return notifyRepository.findByIdAndIndex(id, index);
    }
}
