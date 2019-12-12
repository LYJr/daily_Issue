package com.example.daily_issue.notify.web;

import com.example.daily_issue.notify.service.Notify;
import com.example.daily_issue.notify.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
public class NotifyAPIController {
    @Autowired
    private NotifyService notifyService;

    @PostMapping("")
    public Notify add(@RequestBody Notify notify) {
        notifyService.save(notify);

        return notify;
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return notifyService.deleteById(id);
    }
    @DeleteMapping("/{id}/{index}")
    public Long delete(@PathVariable String id, @PathVariable Long index) {
        return notifyService.deleteByIdAndListIndex(id, index);
    }
    @GetMapping("/{id}")
    public Object get(@PathVariable String id) {
        return notifyService.findById(id);
    }

    @GetMapping("/{id}/{index}")
    public Object get(@PathVariable String id, @PathVariable Long index) {
        return notifyService.findByIdAndIndex(id, index);
    }
}
