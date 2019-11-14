package com.example.daily_issue.accounting.domain;

import javax.persistence.*;

@Entity
@Table(name = "tb_record_type")
public class RecordType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String name;

}
