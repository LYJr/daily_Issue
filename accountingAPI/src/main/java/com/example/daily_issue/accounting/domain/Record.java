package com.example.daily_issue.accounting.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    public Record.Response convertToResponse(){
        return new Record.Response(this);
    }

    public Record.Request converToRequest(){
        return new Record.Request(this);
    }


    public static class Response {
        private Long id;
        private LocalDate recordDate;
        private String memberName;
        private int price;
        private String recordTypeName;

        public Response(Record record) {
            this.id = record.id;
            this.recordDate = record.recordDate;
            this.memberName = record.memberName;
            this.price = record.price;
            this.recordTypeName = record.recordType.name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDate getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(LocalDate recordDate) {
            this.recordDate = recordDate;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getRecordTypeName() {
            return recordTypeName;
        }

        public void setRecordTypeName(String recordTypeName) {
            this.recordTypeName = recordTypeName;
        }

    }

    public static class Request{
        private LocalDate recordDate;
        private String memberName;
        private Integer price;

        public Request(LocalDate recordDate, String memberName, Integer price) {
            this.recordDate = recordDate;
            this.memberName = memberName;
            this.price = price;
        }

        public Request(Record record) {
            this.recordDate = record.recordDate;
            this.memberName = record.memberName;
            this.price = record.price;
        }

        public Record convertToRecord(){
            Record record = new Record();
            record.setRecordDate(this.recordDate);
            record.setMemberName(this.memberName);
            record.setPrice(this.price);
            return record;
        }

        public LocalDate getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(LocalDate recordDate) {
            this.recordDate = recordDate;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }

    public void update(Record newRecord){
        this.id = newRecord.id;
        this.recordType = newRecord.recordType;
        this.recordDate = newRecord.recordDate;
        this.memberName = newRecord.memberName;
        this.price = newRecord.price;
    }

}
