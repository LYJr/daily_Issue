package com.example.daily_issue.accounting.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.daily_issue.accounting.domain.Record;

public interface RecordService {

    Record.Response getRecordByCurrentDate(LocalDate date);

    List<Record.Response> getRecordListByBetweenDate(LocalDate left, LocalDate right);

    List<Record.Response> getCurrentTypeRecords(Long typeId);

    List<Record.Response> getOverPriceRecords(int price, boolean hasEquals);

    List<Record.Response> getLessPriceRecords(int price, boolean hasEquals);


    void save(Record.Request request, Long typeId);

    void update(Record record);

    void delete(Long id);
}
