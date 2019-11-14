package com.example.daily_issue.accounting.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "tb_record")
@Getter @Setter
public class Record {

    public Record() { }

    public Record(RecordType recordType, LocalDate recordDate, String memberName, Integer price) {
        this.recordType = recordType;
        this.recordDate = recordDate;
        this.memberName = memberName;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_recordType")
    private  RecordType recordType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate recordDate;

    @Column
    private String memberName;

    @Column
    private Integer price;

    public void update(Record newRecord){
        this.id = newRecord.id;
        this.recordType = newRecord.recordType;
        this.recordDate = newRecord.recordDate;
        this.memberName = newRecord.memberName;
        this.price = newRecord.price;
    }
}
