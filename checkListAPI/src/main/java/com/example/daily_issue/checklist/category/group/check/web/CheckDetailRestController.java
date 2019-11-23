package com.example.daily_issue.checklist.category.group.check.web;

import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;
import com.example.daily_issue.checklist.category.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.category.group.check.service.CheckDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group/{todoGroupId}/detail")
public class CheckDetailRestController {
    private CheckDetailService checkDetailService;

    public CheckDetailRestController(CheckDetailService checkDetailService) {
        this.checkDetailService = checkDetailService;
    }

    @GetMapping("/")
    public ResponseEntity list(@PathVariable Integer todoGroupId) {
        List<CheckDetail> allByTodoGroupId = checkDetailService.findAllByTodoGroupId(todoGroupId);

        List<CheckDetail.Response> responseList = new ArrayList<>();
        if(allByTodoGroupId != null) {
            for(CheckDetail checkDetail : allByTodoGroupId) {
                responseList.add(checkDetail.convertToResponse());
            }
        }
        return ResponseEntity.ok().body(responseList);
    }

    @PostMapping("/")
    public ResponseEntity save(@PathVariable Integer todoGroupId, @RequestBody CheckDetail.Request request) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(checkDetailService.save(todoGroupId, request).convertToResponse());
    }

    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable("todoGroupId") Integer todoGroupId, @PathVariable("id") Integer id, @RequestBody CheckDetail.Request request) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(checkDetailService.updateByTodoGroupIdAndId(todoGroupId, id, request).convertToResponse());
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity updateComplete(@PathVariable("todoGroupId") Integer todoGroupId, @PathVariable("id") Integer id,@RequestBody CheckDetail.Request request) throws ObjectNotFoundException {

        return ResponseEntity.ok().body(checkDetailService.updateCompleteByTodoGroupIdAndId(todoGroupId, id, request.isComplete()).convertToResponse());
    }
}
