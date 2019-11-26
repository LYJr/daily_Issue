package com.example.daily_issue.checklist.group.check.web;

import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.check.service.CheckDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/detail")
public class CheckDetailRestController {
    private CheckDetailService checkDetailService;

    public CheckDetailRestController(CheckDetailService checkDetailService) {
        this.checkDetailService = checkDetailService;
    }

    @GetMapping("/{todoGroupId}")
    public ResponseEntity list(@PathVariable Integer todoGroupId) {
        return ResponseEntity.ok().body(checkDetailService.findAllByTodoGroupId(todoGroupId));
    }

    @PostMapping("/{todoGroupId}")
    public ResponseEntity list(CheckDetail.Request request) {
        return ResponseEntity.ok().body(checkDetailService.save(request));
    }
}
