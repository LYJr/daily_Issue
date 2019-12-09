package com.example.daily_issue.notify.web;

import com.example.daily_issue.notify.service.Notify;
import com.example.daily_issue.notify.service.impl.NotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Component
public class TestController  implements ApplicationRunner {
    @Autowired
    private NotifyRepository notifyRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Notify notify = new Notify();
        notify.setTitle("sooraa");
        notify.setContents("titltltltl");
        notifyRepository.save(notify);

        Optional<Notify> byId = notifyRepository.findById("sooraa");
        if(byId.isPresent()) {
            System.out.println(byId.get().getContents());
        }
    }
}
