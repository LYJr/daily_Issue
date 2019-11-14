package com.example.daily_issue.accounting.service;

import java.time.LocalDate;
import java.util.List;

import com.example.daily_issue.accounting.domain.Record;

public interface RecordService {

    Record getRecordByCurrentDate(LocalDate date);

    List<Record> getRecordListByBetweenDate(LocalDate left, LocalDate right);

    List<Record> getCurrentTypeRecords(Long typeId);

    List<Record> getOverPriceRecords(int price, boolean hasEquals);

    List<Record> getLessPriceRecords(int price, boolean hasEquals);


}
