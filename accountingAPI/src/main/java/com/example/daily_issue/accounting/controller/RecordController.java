package com.example.daily_issue.accounting.controller;

import com.example.daily_issue.accounting.domain.Record;
import com.example.daily_issue.accounting.service.RecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/")
    public ResponseEntity<?> registRecord(Record record){
        recordService.create(record);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value="/read")
    public ResponseEntity<?> readByDate(LocalDate date){
        Record.Response record = recordService.getRecordByCurrentDate(date);
        System.out.println("recordTypeName : "+record.getRecordTypeName());
        return ResponseEntity.ok(record);
    }

    @GetMapping("/read/between")
    public ResponseEntity<?> readByBetweenDate(
            LocalDate left,
            LocalDate right
    ){
        List<Record.Response> recordList = recordService.getRecordListByBetweenDate(left, right);
        return ResponseEntity.ok(recordList);
    }

    @GetMapping(value="/read/{recordType}")
    public ResponseEntity<?> readByRecordType(@PathVariable Long recordType){
        List<Record.Response> recordList = recordService.getCurrentTypeRecords(recordType);
        return ResponseEntity.ok(recordList);
    }

    @GetMapping("/read/over")
    public ResponseEntity<?> readByOverPrice(Integer price, boolean hasEquals){
        List<Record.Response> recordList = recordService.getOverPriceRecords(price, hasEquals);
        return ResponseEntity.ok(recordList);
    }

    @GetMapping("/read/less")
    public ResponseEntity<?> readByLessPrice(Integer price, boolean hasEquals){
        List<Record.Response> recordList = recordService.getLessPriceRecords(price, hasEquals);
        return ResponseEntity.ok(recordList);
    }

    @PutMapping("/")
    public ResponseEntity renewalRecord(Record record){
        recordService.update(record);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/")
    public ResponseEntity deleteRecord(Long id){
        recordService.delete(id);
        return ResponseEntity.ok("{}");
    }


}
