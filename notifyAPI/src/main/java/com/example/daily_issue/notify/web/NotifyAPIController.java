package com.example.daily_issue.notify.web;

import com.example.daily_issue.notify.service.Message;
import com.example.daily_issue.notify.service.Notify;
import com.example.daily_issue.notify.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotifyAPIController {
    @Autowired
    private NotifyService notifyService;

    @GetMapping("/add")
    public String add() {
        Notify notify = new Notify();
        notify.setUserId("bobongg");
        for(int i = 0; i<10; i++) {
            Message message = new Message();
            message.setTitle("title"+i);
            message.setMessage("message"+i);
            notify.getMessages().add(message);
        }
        notifyService.save(notify);
        return "OK";
    }
    @GetMapping("/del")
    public String del() {
        notifyService.deleteByIdAndListIndex("bobongg", 3L);

        return "OK";
    }
    @GetMapping("/get")
    public Object get() {
        return notifyService.findById("bobongg");
    }
}
