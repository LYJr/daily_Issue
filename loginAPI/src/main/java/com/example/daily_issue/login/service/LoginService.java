package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.User;
import com.example.daily_issue.login.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    private User findById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean login(String userId, String password) {
        User originUser = findById(userId);
        if (originUser.idConfirm(userId)) {

            if(originUser.passwordConfirm(password)){
                return true;
            }
        }
        return false;
    }
}
