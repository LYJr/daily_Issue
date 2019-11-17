package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.attr.ServiceURLAttributes;
import com.example.daily_issue.calendar.domain.vo.req.BasicTaskReq;
import com.example.daily_issue.calendar.domain.vo.req.RepeatableTaskReq;
import com.example.daily_issue.calendar.domain.vo.resp.BasicTaskResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalendarControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;


    private static List<BasicTaskResp> tasks = new ArrayList<>();






    @Test @Order(1)
    @DisplayName("일정생성 / anonymous")
    @WithAnonymousUser
    public void task_create_anonymous() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(getTaskReq(20));

        // then
        mockMvc.perform(
                    post(ServiceURLAttributes.TASK_CREATE.getUrl())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(req)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test @Order(2)
    @DisplayName("일정생성 / 없는 사용자")
    public void task_create_wrongUser() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(getTaskReq(20));

        // then
        MvcResult mvcResult = mockMvc.perform(
                post(ServiceURLAttributes.TASK_CREATE.getUrl())
                        .with(httpBasic("wrong user", "password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test @Order(3)
    @DisplayName("일정생성 / 잘못된 비밀번호")
    public void task_create_wrongPassword() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(getTaskReq(20));

        // then
        MvcResult mvcResult = mockMvc.perform(
                post(ServiceURLAttributes.TASK_CREATE.getUrl())
                        .with(httpBasic("user1", "wrong password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test @Order(4)
    @DisplayName("일정생성 / 올바른 사용자")
    public void task_create_correctUser() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(getTaskReq(20));

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
        tasks.add(taskResp);

        System.out.println(tasks.size());
        System.out.println(tasks.get(0));
    }

/////////////////////////////////////////////////////////////////////////


    @Test @Order(5)
    @DisplayName("일정수정 / anonymous")
    @WithAnonymousUser
    public void task_modify_anonymous() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(modifyBasicTask(getTaskReq(20)));


        // then
        mockMvc.perform(
                put(ServiceURLAttributes.TASK_MODIFY.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test @Order(6)
    @DisplayName("일정수정 / 없는 사용자")
    public void task_modify_wrongUser() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(modifyBasicTask(getTaskReq(20)));

        // then
        MvcResult mvcResult = mockMvc.perform(
                put(ServiceURLAttributes.TASK_MODIFY.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("wrong user", "password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test @Order(7)
    @DisplayName("일정수정 / 잘못된 비밀번호")
    public void task_modify_wrongPassword() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(modifyBasicTask(getTaskReq(20)));

        // then
        MvcResult mvcResult = mockMvc.perform(
                put(ServiceURLAttributes.TASK_MODIFY.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("user1", "wrong password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }


    @Test @Order(8)
    @DisplayName("일정수정 / 없는 일정 수정 시도")
    public void task_modify_nonExistTask() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(modifyBasicTask(getTaskReq(20)));

        // then
        MvcResult mvcResult = mockMvc.perform(
                put(ServiceURLAttributes.TASK_MODIFY.getUrl() + "?taskId=" + tasks.size()+1)
                        .with(httpBasic("user1", "password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect((r) -> r.getResponse().getContentAsString().isEmpty())
                .andReturn();
    }

    @Test @Order(9)
    @DisplayName("일정수정 / 권한없는 사용자 일정 수정 시도")
    // task 1은 user1이 생성, user2계정 로그인 후 수정시도
    public void task_modify_unauthorizedTask() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(modifyBasicTask(getTaskReq(20)));

        // then
        MvcResult mvcResult = mockMvc.perform(
                put(ServiceURLAttributes.TASK_MODIFY.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("user2", "password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect((r) -> r.getResponse().getContentAsString().isEmpty())
                .andReturn();
    }



    @Test @Order(10)
    @DisplayName("일정수정 / 올바른 사용자")
    public void task_modify_correctUser() throws Exception {
        // given
        String req = objectMapper.writeValueAsString(modifyBasicTask(getTaskReq(20)));

        // then
        MvcResult mvcResult = mockMvc.perform(
                put(ServiceURLAttributes.TASK_MODIFY.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("user1", "password"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(req)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BasicTaskResp taskResp = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BasicTaskResp.class);
        assertThat(taskResp.getLastModifiedDate()).isNotEqualTo(taskResp.getCreatedDate());
        assertThat(tasks.get(0).getLastModifiedDate()).isNotEqualTo(taskResp.getLastModifiedDate());
    }


/////////////////////////////////////////////////////////////////////////


    @Test @Order(11)
    @DisplayName("일정삭제 / anonymous")
    @WithAnonymousUser
    public void task_delete_anonymous() throws Exception {
        // then
        mockMvc.perform(
                delete(ServiceURLAttributes.TASK_DELETE.getUrl() + "?taskId=" + tasks.get(0).getId())
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test @Order(12)
    @DisplayName("일정삭제 / 없는 사용자")
    public void task_delete_wrongUser() throws Exception {
        // then
        MvcResult mvcResult = mockMvc.perform(
                delete(ServiceURLAttributes.TASK_DELETE.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("wrong user", "password"))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test @Order(13)
    @DisplayName("일정삭제 / 잘못된 비밀번호")
    public void task_delete_wrongPassword() throws Exception {
        // then
        MvcResult mvcResult = mockMvc.perform(
                delete(ServiceURLAttributes.TASK_DELETE.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("user1", "wrong password"))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }


    @Test @Order(14)
    @DisplayName("일정삭제 / 없는 일정 삭제 시도")
    public void task_delete_nonExistTask() throws Exception {
        // then
        MvcResult mvcResult = mockMvc.perform(
                delete(ServiceURLAttributes.TASK_DELETE.getUrl() + "?taskId=" + tasks.size()+1)
                        .with(httpBasic("user1", "password"))
        )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect((r) -> r.getResponse().getContentAsString().isEmpty())
                .andReturn();
    }

    @Test @Order(15)
    @DisplayName("일정삭제 / 권한없는 사용자 일정 삭제 시도")
    // task 1은 user1이 생성, user2계정 로그인 후 삭제시도
    public void task_delete_unauthorizedTask() throws Exception {
        // then
        MvcResult mvcResult = mockMvc.perform(
                delete(ServiceURLAttributes.TASK_DELETE.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("user2", "password"))
        )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect((r) -> r.getResponse().getContentAsString().isEmpty())
                .andReturn();
    }



    @Test @Order(16)
    @DisplayName("일정삭제 / 올바른 사용자")
    public void task_delete_correctUser() throws Exception {
        // then
        MvcResult mvcResult = mockMvc.perform(
                delete(ServiceURLAttributes.TASK_DELETE.getUrl() + "?taskId=" + tasks.get(0).getId())
                        .with(httpBasic("user1", "password"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BasicTaskResp taskResp = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BasicTaskResp.class);
    }










    public BasicTaskReq getTaskReq(int seq) throws JsonProcessingException {

        if(seq == 0)
        {
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());

            seq = random.nextInt(10-1)+1;
        }

        BasicTaskReq req = new BasicTaskReq();
        req.setIsAllDay(seq/2 == 0);
        req.setPlace("test place - " + seq);
        req.setComment("test comment - " + seq);

        req.setTaskStartDate(LocalDate.of(2019, 11, 1));
        req.setTaskEndDate(LocalDate.of(2019, 11, 30));
        req.setTaskStartTime(LocalTime.of(10, 0, 0));
        req.setTaskEndTime(LocalTime.of(22, 30, 30));
        req.setTitle("title");
        req.setColor("color");

        return req;
    }


    public BasicTaskReq getTaskReqIncludeRepeatableTask(BasicTaskReq req)
    {
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

        return req;
    }

    private BasicTaskReq modifyBasicTask(BasicTaskReq req)
    {
        req.setTitle(req.getTitle()+" : modified");
        return req;
    }
}