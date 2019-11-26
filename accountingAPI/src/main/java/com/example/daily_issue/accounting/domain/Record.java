package com.example.daily_issue.accounting.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 가계부의 기록에 해당하는 엔티티
 * @author 진환
 */
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

    public void update(Record newRecord){
        this.id = newRecord.id;
        this.recordType = newRecord.recordType;
        this.recordDate = newRecord.recordDate;
        this.memberName = newRecord.memberName;
        this.price = newRecord.price;
    }


    @Getter @Setter
    public static class Response {
        private Long id;
        private LocalDate recordDate;
        private String memberName;
        private int price;
        private String recordTypeName;

        public Response(Long id, LocalDate recordDate, String memberName, int price, String recordTypeName) {
            this.id = id;
            this.recordDate = recordDate;
            this.memberName = memberName;
            this.price = price;
            this.recordTypeName = recordTypeName;
        }

        public Response(Record record) {
            this.id = record.id;
            this.recordDate = record.recordDate;
            this.memberName = record.memberName;
            this.price = record.price;
            this.recordTypeName = record.recordType.name;
        }

    }

    @Getter @Setter
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
    }

}
