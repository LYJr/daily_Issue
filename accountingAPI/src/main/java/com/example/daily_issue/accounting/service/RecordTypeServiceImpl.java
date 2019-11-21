package com.example.daily_issue.accounting.service;

import com.example.daily_issue.accounting.domain.RecordType;
import com.example.daily_issue.accounting.repository.RecordTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RecordTypeServiceImpl implements RecordTypeService {

    private final RecordTypeRepository recordTypeRepository;

    public RecordTypeServiceImpl(RecordTypeRepository recordTypeRepository) {
        this.recordTypeRepository = recordTypeRepository;
    }

    @Override
    public void createRecordType(RecordType recordType) {
        recordTypeRepository.save(recordType);
    }

    @Override
    public RecordType getRecordTypeById(Long typeId) {

        RecordType recordType = recordTypeRepository.findById(typeId).orElse(null);
        if(recordType == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "record type not found [id="+typeId+"]");
        return recordType;
    }

    @Override
    public RecordType getRecordTypeByName(String name) {

        RecordType recordType = recordTypeRepository.findByName(name).orElse(null);
        if(recordType == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "record type not found [name="+name+"]");

        return recordType;
    }

    @Override
    public List<RecordType> getAllRecordType() {
        return recordTypeRepository.findAll();
    }

    @Override
    public void removeRecordType(Long id) {
        recordTypeRepository.deleteById(id);
    }

    @Override
    public RecordType updateRecordType(RecordType recordType) {

        Optional<RecordType> targetType = recordTypeRepository.findById(recordType.getId());
        if(targetType.isPresent())
            return recordTypeRepository.save(recordType);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"recordType not found [id="+recordType.id+"]");
    }
}
