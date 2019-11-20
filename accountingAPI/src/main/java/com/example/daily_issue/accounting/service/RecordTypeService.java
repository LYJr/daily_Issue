package com.example.daily_issue.accounting.service;

import com.example.daily_issue.accounting.domain.RecordType;

import java.util.List;

public interface RecordTypeService {
    void createRecordType(RecordType recordType);
    RecordType getRecordTypeById(Long typeId);
    RecordType getRecordTypeByName(String name);
    List<RecordType> getAllRecordType();
}
