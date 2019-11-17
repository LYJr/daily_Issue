package com.example.daily_issue.calendar.domain.vo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class BasicTaskReq {

    private Set<RepeatableTaskReq> repeatableTaskReqs;

    /* 일정 종일여부
    = 일 단위로 일정관리 여부
    ( true : 시간정보 제외됨) */
    private Boolean isAllDay;

    /* 반복여부 */
    private boolean isRepeatable = false;

    /* 일정 시작일 */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate taskStartDate;
    /* 일정 시작시 */
    @DateTimeFormat(pattern = "kk:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private LocalTime taskStartTime;

    /* 일정 종료일 */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate taskEndDate;
    /* 일정 종료시 */
    @DateTimeFormat(pattern = "kk:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private LocalTime taskEndTime;

    /* 일정 제목 */
    @NotNull
    private String title;

    /* 일정 상세설명 */
    private String comment;

    /* 일정 위치 / 장소 */
    private String place;


    /* 일정 표기 색 (default : black) */
    /*
     * rgb : rgb(0, 0, 0)
     * cmyk : cmyk(0%, 0%, 0%, 100%)
     * hsl : hsl(0, 0%, 0%)
     * hex : #000000
     * */
    private String color;
}
