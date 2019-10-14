package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.User;
import com.example.daily_issue.login.domain.repository.UserRepository;
import com.example.daily_issue.login.dto.UserDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * 아이디, 핸드폰 번호 확인 메서드<작업중>
     * @param userDto
     * @return
     * @throws IllegalArgumentException
     */
    public User userConfirm (UserDto userDto) throws IllegalArgumentException{
        User user = userRepository.findByUserId(userDto.getUserId());

//        try {
//
//        } catch ()

        return userDto.toUser();
    }

    private String conversion (String input) {
        return (input);
    }



}
