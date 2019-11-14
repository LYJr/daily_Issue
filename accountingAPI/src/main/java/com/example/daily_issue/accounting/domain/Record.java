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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
}
