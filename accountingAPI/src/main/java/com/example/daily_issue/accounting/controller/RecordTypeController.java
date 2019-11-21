package com.example.daily_issue.accounting.controller;

import com.example.daily_issue.accounting.domain.RecordType;
import com.example.daily_issue.accounting.service.RecordTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recordType")
public class RecordTypeController {

    private final RecordTypeService recordTypeService;

    public RecordTypeController(RecordTypeService recordTypeService) {
        this.recordTypeService = recordTypeService;
    }

    @PostMapping("/regist")
    public ResponseEntity<?> createRecordType(RecordType recordType){
        recordTypeService.createRecordType(recordType);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getRecord(Long id){
        RecordType recordType =  recordTypeService.getRecordTypeById(id);
        return ResponseEntity.ok(recordType);
    }

    @GetMapping("/name")
    public ResponseEntity<?> getRecord(String name){
        RecordType recordType = recordTypeService.getRecordTypeByName(name);
        return ResponseEntity.ok(recordType);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRecordType(RecordType recordType){
        recordTypeService.updateRecordType(recordType);
        return ResponseEntity.ok(recordType);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeRecordType(Long id){
        recordTypeService.removeRecordType(id);
        return ResponseEntity.ok("{}");
    }


}
