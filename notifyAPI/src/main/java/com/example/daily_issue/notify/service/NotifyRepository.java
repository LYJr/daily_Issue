package com.example.daily_issue.notify.service;

import com.example.daily_issue.notify.service.Notify;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class NotifyRepository {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public Notify save(Notify notify) {
        List<Message> messages = notify.getMessages();
        for(Message message : messages) {
            redisTemplate.opsForList().rightPush(notify.getKey(), message);
        }
        return notify;
    }
    public Notify findById(String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Notify notify = new Notify();
        notify.setUserId(id);

        List<Object> range = redisTemplate.opsForList().range(notify.getKey(), 0L, Long.MAX_VALUE);
        for (Object o : range) {
            Message message = objectMapper.convertValue(o, Message.class);
            notify.getMessages().add(message);
        }
        return notify;
    }
    public void deleteById(String id) {
        Notify notify = new Notify();
        notify.setUserId(id);

        redisTemplate.delete(notify.getKey());
    }
    public void deleteByIdAndListIndex(String id, long index) {
        Notify notify = new Notify();
        notify.setUserId(id);
        ListOperations<String, Object> stringObjectListOperations = redisTemplate.opsForList();;
        stringObjectListOperations.remove(notify.getKey(), 1, stringObjectListOperations.index(id, index));
    }
}
