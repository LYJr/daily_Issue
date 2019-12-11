package com.example.daily_issue;

import com.example.daily_issue.notify.NotifyApplication;
import com.example.daily_issue.notify.service.Message;
import com.example.daily_issue.notify.service.Notify;
import com.example.daily_issue.notify.service.NotifyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = NotifyApplication.class)
@Slf4j
public class NotifyApplicationTest {
    @Autowired
    private NotifyRepository notifyRepository;


    @Test
    @Rollback
    @Transactional
    public void redisSaveTest () {
        System.out.println("hellllllllllllllohjghjhgjghjkghjk");


        List<Notify> arrayList = new ArrayList<>();
        Long start = System.currentTimeMillis();
        for(int i = 0; i<100; i++) {
            Notify notify = new Notify();
            notify.setUserId("soora");

            notify.setMessages(new ArrayList<>());
            for(int j = 0; j<1000; j++) {
                Message message = new Message();
                message.setMessage("me"+j);
                message.setTitle("ti"+j);
                notify.getMessages().add(message);
            }

            arrayList.add(notify);
        }
//        notifyRepository.saveAll(arrayList);
        System.out.println(System.currentTimeMillis() - start);
    }
}