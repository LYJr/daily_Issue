package com.example.daily_issue.accounting.controller;

import com.example.daily_issue.accounting.domain.Record;
import com.example.daily_issue.accounting.service.RecordService;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping(value="/read")
    public Record readByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return recordService.getRecordByCurrentDate(date);
    }

    @GetMapping("/read/between")
    public List<Record> readByBetweenDate(LocalDate left, LocalDate right){
        return recordService.getRecordListByBetweenDate(left, right);
    }

    @GetMapping(value="/read/{recordType}")
    public List<Record> readByRecordType(@PathVariable Long recordType){
        return recordService.getCurrentTypeRecords(recordType);
    }

    @GetMapping("/read/over")
    public List<Record> readByOverPrice(Integer price, boolean hasEquals){
        return recordService.getOverPriceRecords(price, hasEquals);
    }

    @GetMapping("/read/less")
    public List<Record> readByLessPrice(Integer price, boolean hasEquals){
        return recordService.getLessPriceRecords(price, hasEquals);
    }


}
