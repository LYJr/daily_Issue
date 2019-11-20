package com.example.daily_issue.checklist.group.check.web;

import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;
import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.check.service.CheckDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/{todoGroupId}/detail")
public class CheckDetailRestController {
    private CheckDetailService checkDetailService;

    public CheckDetailRestController(CheckDetailService checkDetailService) {
        this.checkDetailService = checkDetailService;
    }

    @GetMapping("/")
    public ResponseEntity list(@PathVariable Integer todoGroupId) {
        return ResponseEntity.ok().body(checkDetailService.findAllByTodoGroupId(todoGroupId));
    }

    @PostMapping("/")
    public ResponseEntity save(@PathVariable Integer todoGroupId, @RequestBody CheckDetail request) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(checkDetailService.save(todoGroupId, request).convertToResponse());
    }

    @PostMapping("/{id}")
    public ResponseEntity update(CheckDetail.Request request, CheckDetail checkDetail) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(checkDetailService.updateByTodoGroupIdAndId(request.getTodoGroupId(), request.getId(), checkDetail).convertToResponse());
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity updateComplete(CheckDetail.Request request) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(checkDetailService.updateCompleteByTodoGroupIdAndId(request.getTodoGroupId(), request.getId(), request.isComplete()));

    }
}
