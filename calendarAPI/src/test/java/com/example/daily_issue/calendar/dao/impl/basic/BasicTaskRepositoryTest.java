package com.example.daily_issue.calendar.dao.impl.basic;

import com.example.daily_issue.calendar.attr.ServiceURLAttributes;
import com.example.daily_issue.calendar.dao.impl.member.MemberRepository;
import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.domain.RepeatableTask;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
import com.example.daily_issue.calendar.ro.BasicTaskResp;
import com.example.daily_issue.calendar.ro.RepeatableTaskReq;
import com.example.daily_issue.calendar.security.service.SecurityService;
import com.example.daily_issue.calendar.service.CalendarService;
import com.example.daily_issue.calendar.service.TaskDisplayService;
import com.example.daily_issue.calendar.service.util.CalendarCalculator;
import com.example.daily_issue.calendar.vo.DateRange;
import com.example.daily_issue.login.domain.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

/**
 *
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BasicTaskRepositoryTest {

//    @Autowired
//    RepeatableTaskRepository repeatableTaskRepository;

    @Autowired
    BasicTaskRepository basicTaskRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CalendarService calendarService;

    @Autowired
    TaskDisplayService taskDisplayService;

    @Autowired
    SecurityService securityService;

    @Autowired
    CalendarCalculator calculator;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;


    private Member member;

    @BeforeEach
    public void setUp()
    {
        member = memberRepository.findByUserId("user1");
        System.out.println(member);
    }


    @Test
    @Rollback(value = false)
    @DisplayName("일반 일정 생성")
    @Order(1)
    public void createBasicTask() throws Exception {

        List<BasicTaskResp> tasks = new ArrayList<>();

        int count = 2;
        while (count > 0)
        {
            // given
            //String req = objectMapper.writeValueAsString(getTaskReq(count));
            String req = objectMapper.writeValueAsString(getTaskReqIncludeRepeatableTask(getTaskReq(count)));

            // then
            MvcResult mvcResult = mockMvc.perform(
                    post(ServiceURLAttributes.TASK_CREATE.getUrl())
                            .with(httpBasic(member.getUserId(), member.getPassword()))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(req)
            )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            BasicTaskResp taskResp = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BasicTaskResp.class);
            tasks.add(taskResp);

            count--;
        }


        System.out.println(tasks.size());
    }

    @Test
    @Rollback(value = false)
    @DisplayName("사용자의 task 가져오기")
    @Order(2)
    public void getBasicTasksByMember()
    {
        List<BasicTask> tasks = basicTaskRepository.findByTaskOwner(member);
        System.out.println(tasks.size());
    }

    @Test
    @Rollback(value = false)
    @DisplayName("특정 기간 내 사용자의 task 가져오기")
    @Order(3)
    public void getBasicTasksByMemberAndDisplayDateRange()
    {
        securityService.setMember(member.getUserId());

        DateRange displayDateRange = calculator.getDisplayDateRange(ChronoUnit.MONTHS, LocalDate.of(2019, 11, 01));

        //List<BasicTask> tasks = basicTaskRepository.findByCreatedByAndTaskStartDateBetween(member, displayDateRange.getStartDate(), displayDateRange.getEndDate());
        List<BasicTask> tasks = basicTaskRepository.findByDisplayableBasicTasks(displayDateRange);
        List<RepeatableTask> repeatableTasks = basicTaskRepository.findByDisplayableRepeatableTasks(displayDateRange);

        System.out.println(tasks.size());
        System.out.println(repeatableTasks.size());

        /*repeatableTasks.forEach(task -> {
            System.out.println("############# weeks ##########");
            Set<LocalDate> repeatDates = taskDisplayService.listRepeatedTaskByDayOfWeeks(displayDateRange, task);
            System.out.println(repeatDates);
        });

        repeatableTasks.forEach(task -> {
            System.out.println("############# days ##########");
            Set<LocalDate> repeatDates = taskDisplayService.listRepeatedTaskBySpecifiedDays(displayDateRange, task);
            System.out.println(repeatDates);
        });*/

        repeatableTasks.forEach(task -> {
            System.out.println("############# distance ##########");
            Set<LocalDate> repeatDates = taskDisplayService.listRepeatedTaskByDistance(displayDateRange, task);
            System.out.println(repeatDates);
        });


    }












    public BasicTaskReq getTaskReq(int seq) throws JsonProcessingException {

        if(seq == 0)
        {
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());

            seq = random.nextInt(100000-1)+1;
        }

        BasicTaskReq req = new BasicTaskReq();
        req.setIsAllDay(seq/2 == 0);
        req.setPlace("test place - " + seq);
        req.setComment("test comment - " + seq);

        req.setTaskStartDate(LocalDate.of(2019, 11, 1).plusDays(seq));
        req.setTaskEndDate(LocalDate.of(2019, 11, 30).plusDays(seq));
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
        //rreq2.setRepeatEndDate(LocalDate.of(2019, 11, 20));
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

        req.setRepeatable(true);
        req.setRepeatableTaskReqs(repeatableTaskReqs);

        return req;
    }
}