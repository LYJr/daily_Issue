package com.example.daily_issue.accounting.service;

import com.example.daily_issue.accounting.domain.Record;
import com.example.daily_issue.accounting.domain.RecordType;
import com.example.daily_issue.accounting.repository.RecordRepository;
import com.example.daily_issue.accounting.repository.RecordTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordTypeService recordTypeService;

    public RecordServiceImpl(RecordRepository recordRepository, RecordTypeService recordTypeService) {
        this.recordRepository = recordRepository;
        this.recordTypeService = recordTypeService;
    }

    @Override
    public Record.Response getRecordByCurrentDate(LocalDate date) {
        return recordRepository.findByRecordDate(date).convertToResponse();
    }

    @Override
    public List<Record.Response> getRecordListByBetweenDate(LocalDate left, LocalDate right) {
        List<Record> recordList = recordRepository.findAllByRecordDateBetween(left, right);
        List<Record.Response> responseList = new ArrayList<>();

        for(Record record : recordList){
            responseList.add(record.convertToResponse());
        }
        return responseList;
    }

    @Override
    public List<Record.Response> getCurrentTypeRecords(Long typeId) {
        RecordType recordType = recordTypeService.getRecordTypeById(typeId);
        if(recordType == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"can't find current record type [id="+typeId+"]");
        List<Record> recordList = recordRepository.findAllByRecordType(recordType);
        return convertRecordToResponse(recordList);
    }

    @Override
    public List<Record.Response> getOverPriceRecords(int price, boolean hasEquals) {
        List<Record> recordList = null;
        if(hasEquals)
            recordList = recordRepository.findAllByPriceGreaterThanEqual(price);
        else
            recordList = recordRepository.findAllByPriceIsLessThan(price);
        return convertRecordToResponse(recordList);
    }

    @Override
    public List<Record.Response> getLessPriceRecords(int price, boolean hasEquals) {
        List<Record> recordList = null;
        if(hasEquals)
            recordList = recordRepository.findAllByPriceIsLessThanEqual(price);
        else
            recordList = recordRepository.findAllByPriceIsLessThan(price);
        return convertRecordToResponse(recordList);
    }

    @Override
    public void save(Record.Request request, Long typeId) {
        RecordType type = recordTypeService.getRecordTypeById(typeId);
        Record record = request.convertToRecord();
        record.setRecordType(type);
        recordRepository.save(record);
    }

    @Override
    public void update(Record updateRecord) {
        if(updateRecord == null || updateRecord.getId() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"record not found");
        Record record = recordRepository.findById(updateRecord.getId()).orElse(null);
        record.update(updateRecord);
        recordRepository.save(record);
    }

    @Override
    public void delete(Long id) {
        recordRepository.deleteById(id);
    }

    public List<Record.Response> convertRecordToResponse(List<Record> list){
        List<Record.Response> responseList = new ArrayList<>();

        for(Record record : list){
            responseList.add(record.convertToResponse());
        }
        return responseList;
    }
}
