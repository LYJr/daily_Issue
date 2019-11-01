package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.repository.UserRepository;
import com.example.daily_issue.login.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JoinServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserDto userDto = new UserDto("id", "pw", "n", "ok", "910203", "01022443513");

    @BeforeEach
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