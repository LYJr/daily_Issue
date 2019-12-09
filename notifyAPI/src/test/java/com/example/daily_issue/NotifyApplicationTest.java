package com.example.daily_issue;

import com.example.daily_issue.notify.NotifyApplication;
import com.example.daily_issue.notify.service.Notify;
import com.example.daily_issue.notify.service.impl.NotifyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = NotifyApplication.class)
@Slf4j
public class NotifyApplicationTest {
    @Autowired
    private NotifyRepository notifyRepository;


    @Test
    @Commit
    @Transactional
    public void redisSaveTest () {
        System.out.println("hellllllllllllllohjghjhgjghjkghjk");
        assertTrue(1 == 1);

        Notify notify = new Notify();
        notify.setTitle("gdsfg");
        notify.setContents("titltltltl");
        notifyRepository.save(notify);
    }
}