package com.example.daily_issue.accounting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
