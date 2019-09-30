package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.repository.UserRepository;
import com.example.daily_issue.login.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class JoinServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserDto userDto = new UserDto("id", "pw", "n", "ok", "910203", "01022443513");

    @Before
    public void insert(){
        when(userRepository.findById(userDto.toUser().getId())).thenReturn(Optional.of(userDto.toUser()));
    }

    @Test
    public void 생성(){
        System.out.println(userDto);

        System.out.println("확인용");
        System.out.println(userDto.toUser());
    }

    @Test
    public void findById(){
        assertThat(userRepository.findById(userDto.toUser().getId()).get().getStatus()).isEqualTo("ok");
    }




}