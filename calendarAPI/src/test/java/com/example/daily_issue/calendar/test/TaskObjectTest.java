package com.example.daily_issue.calendar.test;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-10
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-10)
 */

import com.example.daily_issue.calendar.attr.ServiceURLAttributes;
import com.example.daily_issue.calendar.domain.vo.req.BasicTaskReq;
import com.example.daily_issue.calendar.domain.vo.req.RepeatableTaskReq;
import com.example.daily_issue.calendar.domain.vo.resp.BasicTaskResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TaskObjectTest {

    @Autowired
    MockMvc mockMvc;


    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp()
    {
        objectMapper = new ObjectMapper();;
        //objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }




    @Test @Order(4)
    @DisplayName("일정생성 / 올바른 사용자")
    public void task_create_correctUser() throws Exception {
        // given
        String req = TaskReq();

        // then
        MvcResult mvcResult = mockMvc.perform(
                post(ServiceURLAttributes.TASK_CREATE.getUrl())
                        .with(httpBasic("user1", "password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        BasicTaskResp taskResp = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BasicTaskResp.class);

        System.out.println("taskResp = " + taskResp);
    }














    public String TaskReq() throws JsonProcessingException {
        BasicTaskReq req = new BasicTaskReq();
        req.setIsAllDay(false);
        req.setTaskStartDate(LocalDate.of(2019, 11, 1));
        req.setTaskEndDate(LocalDate.of(2019, 11, 30));
        req.setTaskStartTime(LocalTime.of(10, 0, 0));
        req.setTaskEndTime(LocalTime.of(22, 30, 30));
        req.setTitle("title");
        req.setComment("comment");
        req.setPlace("place");
        req.setColor("color");



        RepeatableTaskReq rreq1 = new RepeatableTaskReq();
        rreq1.setRepeatChronoUnit(ChronoUnit.MONTHS);
        rreq1.setRepeatAmount(2);
        rreq1.setIncludeBaseDate(true);
        rreq1.setRepeatStartDate(LocalDate.of(2019, 11, 10));
        rreq1.setRepeatEndDate(LocalDate.of(2019, 11, 20));
        Set<DayOfWeek> dws1 = new HashSet<>();
        dws1.add(DayOfWeek.TUESDAY);
        dws1.add(DayOfWeek.THURSDAY);
        rreq1.setRepeatDayOfWeeks(dws1);
        Set<Integer> days1 = new HashSet<>();
        days1.add(10);
        days1.add(18);
        days1.add(28);
        rreq1.setRepeatDays(days1);

        RepeatableTaskReq rreq2 = new RepeatableTaskReq();
        rreq2.setRepeatChronoUnit(ChronoUnit.WEEKS);
        rreq2.setRepeatAmount(3);
        rreq2.setIncludeBaseDate(false);
        rreq2.setRepeatStartDate(LocalDate.of(2019, 11, 10));
        rreq2.setRepeatEndDate(LocalDate.of(2019, 11, 20));
        Set<DayOfWeek> dws2 = new HashSet<>();
        dws2.add(DayOfWeek.WEDNESDAY);
        dws2.add(DayOfWeek.FRIDAY);
        rreq2.setRepeatDayOfWeeks(dws2);
        Set<Integer> days2 = new HashSet<>();
        days2.add(11);
        days2.add(19);
        days2.add(29);
        rreq2.setRepeatDays(days2);


        Set<RepeatableTaskReq> repeatableTaskReqs = new HashSet<>();
        repeatableTaskReqs.add(rreq1);
        repeatableTaskReqs.add(rreq2);

        req.setRepeatableTaskReqs(repeatableTaskReqs);


        String result = objectMapper.writeValueAsString(req);

        return result;
    }
}
