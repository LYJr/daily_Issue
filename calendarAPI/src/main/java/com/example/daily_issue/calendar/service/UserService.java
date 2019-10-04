package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.dao.UserRepository;
import com.example.daily_issue.login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User findUser(String userid)
    {
        User user = userRepository.findByUserId(userid);

        return user;
    }
}
