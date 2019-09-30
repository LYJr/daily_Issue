package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.User;
import com.example.daily_issue.login.domain.repository.UserRepository;
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

}
