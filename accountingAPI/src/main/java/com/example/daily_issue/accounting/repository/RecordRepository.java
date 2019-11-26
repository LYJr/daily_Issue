package com.example.daily_issue.accounting.repository;

import com.example.daily_issue.accounting.domain.Record;
import com.example.daily_issue.accounting.domain.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/*
 * TODO: 추후 member별로 식별해서 Authorization
 */
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findByRecordDate(LocalDate date);
    List<Record> findAllByRecordDateBetween(LocalDate left, LocalDate right);
    List<Record> findAllByRecordType(RecordType recordType);
    List<Record> findAllByPriceGreaterThanEqual(int price);
    List<Record> findAllByPriceIsLessThanEqual(int typeId);
    List<Record> findAllByPriceIsLessThan(int typeId);
}
