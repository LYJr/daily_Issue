package com.example.daily_issue.notify.service;

import com.example.daily_issue.notify.service.Notify;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class NotifyRepository {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public Notify save(Notify notify) {
        List<Message> messages = notify.getMessages();
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = redisTemplate.boundHashOps(notify.getKey());
        Long size = stringObjectObjectBoundHashOperations.size();
        Map<Long,Message> allMap = new HashMap<>();
        for(int i = 0; i<messages.size(); i++) {
            Message message = messages.get(i);
            message.setId(size + i);
            allMap.put(message.getId(), message);
        }
        stringObjectObjectBoundHashOperations.putAll(allMap);
        return notify;
    }
    public Notify findById(String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Notify notify = new Notify();
        notify.setUserId(id);

        List<Object> range = redisTemplate.opsForHash().values(notify.getKey());
        for (Object o : range) {
            Message message = objectMapper.convertValue(o, Message.class);
            notify.getMessages().add(message);
        }

        if(notify.getMessages() != null && notify.getMessages().size() > 0){
            Collections.sort(notify.getMessages(), (o1, o2) -> {
                if(o1.getId() < o2.getId()) {
                    return -1;
                }else if(o1.getId() > o2.getId()) {
                    return 1;
                }else {
                    return 0;
                }
            });
        }
        return notify;
    }
    public Notify findByIdAndIndex(String id, Long index) {
        ObjectMapper objectMapper = new ObjectMapper();
        Notify notify = new Notify();
        notify.setUserId(id);

        Object o = redisTemplate.opsForHash().get(notify.getKey(), index);
        Message message = objectMapper.convertValue(o, Message.class);

        notify.getMessages().add(message);
        return notify;
    }
    public boolean deleteById(String id) {
        Notify notify = new Notify();
        notify.setUserId(id);

        return redisTemplate.delete(notify.getKey());
    }
    public Long deleteByIdAndListIndex(String id, long index) {
        Notify notify = new Notify();
        notify.setUserId(id);
        return redisTemplate.opsForHash().delete(notify.getKey(), index);
    }
}


// blacky -> [2:'a', 3:'b', 4:'c', 5:'d']
// blacky -> {key:'1',data:'b'}a
//