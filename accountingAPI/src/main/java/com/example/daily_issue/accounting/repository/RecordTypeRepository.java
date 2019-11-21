package com.example.daily_issue.accounting.repository;

import com.example.daily_issue.accounting.domain.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long> {
    Optional<RecordType> findByName(String name);
}
