package com.example.daily_issue.accounting.service;

import com.example.daily_issue.accounting.domain.Record;
import com.example.daily_issue.accounting.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public Record getRecordByCurrentDate(LocalDate date) {
        return recordRepository.findByRecordDate(date);
    }

    @Override
    public List<Record> getRecordListByBetweenDate(LocalDate left, LocalDate right) {
        return recordRepository.findAllByRecordDateBetween(left, right);
    }

    @Override
    public List<Record> getCurrentTypeRecords(Long typeId) {
        return recordRepository.findAllByRecordType(typeId);
    }

    @Override
    public List<Record> getOverPriceRecords(int price, boolean hasEquals) {
        if(hasEquals)
            return recordRepository.findAllByPriceGreaterThanEqual(price);

        return recordRepository.findAllByPriceIsLessThan(price);
    }

    @Override
    public List<Record> getLessPriceRecords(int price, boolean hasEquals) {
        if(hasEquals)
            return recordRepository.findAllByPriceIsLessThanEqual(price);
        return recordRepository.findAllByPriceIsLessThan(price);
    }
}
