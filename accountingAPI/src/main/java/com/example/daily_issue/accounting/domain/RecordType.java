package com.example.daily_issue.accounting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 가계부의 기록 타입에 해당하는 엔티티
 * @author 진환
 */
@Entity
@Table(name = "tb_record_type")
@Getter @Setter
public class RecordType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String name;

    @JsonIgnore
    @OneToMany(mappedBy = "recordType", cascade = CascadeType.REMOVE)
    List<Record> recordList;


}
