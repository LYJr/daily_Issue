package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.calendar.dao.MemberRepository;
import com.example.daily_issue.calendar.dao.RepeatableTaskRepository;
import com.example.daily_issue.login.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-08
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-08)
 */

/**
 *
 *
 */
//@DataJpaTest
@SpringBootTest
@Rollback(value = false)
class RepeatableTaskTest {

    @Autowired
    RepeatableTaskRepository repeatableTaskRepository;
    @Autowired
    MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp()
    {
        member = memberRepository.findByUserId("user1");
        System.out.println(member);
    }

    @Test
    @DisplayName("반복일정에 대한 테스트 - database column 구조")
    public void RepeatbleTaskTest()
    {
        RepeatableTask task = new RepeatableTask();

        Set<DayOfWeek> dw = new HashSet<>();
        dw.add(DayOfWeek.MONDAY);
        dw.add(DayOfWeek.WEDNESDAY);

        task.getBasicTask().setCreatedBy(member);
        task.getBasicTask().setCreatedDate(LocalDateTime.now());
        task.getBasicTask().setLastModifiedBy(member);
        task.getBasicTask().setLastModifiedDate(LocalDateTime.now());
        task.setRepeatDayOfWeeks(dw);
        repeatableTaskRepository.save(task);


        /*List<RepeatableTask> repeatableTasks = repeatableTaskRepository.findByCreatedBy(member);

        for (RepeatableTask temp : repeatableTasks) {
            temp.getRepeatDayOfWeeks().forEach(System.out::println);
        }*/
    }

}