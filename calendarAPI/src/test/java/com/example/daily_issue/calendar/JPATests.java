package com.example.daily_issue.calendar;

import com.example.daily_issue.calendar.dao.CalendarRepository;
import com.example.daily_issue.calendar.dao.UserRepository;
import com.example.daily_issue.calendar.domain.Task;
import com.example.daily_issue.login.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("local")
/*@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
/*@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)*/
@ComponentScan(basePackages = "com.example.daily_issue")
@Slf4j
public class JPATests {

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void AllUsers()
    {
        List<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();

        User findUser = userRepository.findByUserId("user_id3");
        assertThat(findUser).isNotNull();
    }

    @Test
    @Rollback(false)
    public void UserCRUDTest()
    {
        // given
        User findUser = userRepository.findByUserId("user_id3");

        findUser.setPassword("user_password3333");

        userRepository.save(findUser);

        // then
        assertThat(findUser.getPassword()).isEqualTo("user_password3333");

    }



    @Test
    @Rollback(false)
    public void CRUDTest()
    {
        // given
        //User user = userRepository.findByUserId("user_id3");

        // task 등록
        Task task = new Task();
        //task.setCreatedBy(user);
        task.setCreatedDate(LocalDateTime.now());
        //task.setLastModifiedBy(user);
        task.setLastModifiedDate(LocalDateTime.now());

        task.setStartDateTime(LocalDateTime.now());
        task.setEndDateTime(LocalDateTime.now());
        task.setTitle("title");

        /* Create Task */
        // when
        Task savedTask = calendarRepository.save(task);

        // then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(task.getId());

        /* Find Task */
        Optional<Task> findTask = calendarRepository.findById(task.getId());
        assertThat(findTask).isNotEmpty();
        assertThat(findTask.get()).isEqualTo(task);

        /* Update Task */
        if(findTask.isPresent())
        {
            savedTask = findTask.get();
            System.out.println("####### 3 " + savedTask.getTitle());
            savedTask.setTitle("title2");
            savedTask = calendarRepository.save(savedTask);

            System.out.println("####### 4 " + savedTask.getTitle());

            assertThat(savedTask.getTitle()).isEqualTo("title2");
        }


        /* Delete Task */
        calendarRepository.delete(task);
        findTask = calendarRepository.findById(task.getId());
        assertThat(findTask).isEmpty();
    }


    @Test
    public void copyTest()
    {
        Task originTask = calendarRepository.findById(1L).get();
        System.out.println(originTask);

        Task fixedTask = new Task();
        fixedTask.setPlace("dddddd");


        BeanUtils.copyProperties(fixedTask, originTask, "id");

        System.out.println(fixedTask);
        System.out.println(originTask);
    }

}
