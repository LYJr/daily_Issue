package com.example.daily_issue.login.util;

import com.example.daily_issue.login.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {

    public static final String USER_SESSION_KEY = "sessiondUser";

    private static boolean isLoginUser (HttpSession session) {
        Object loginiUser = session.getAttribute(USER_SESSION_KEY);

        if(loginiUser == null){
            return false;
        }
        return true;
    }

    public static User getSessiondUser (HttpSession session){

        if(isLoginUser(session)){
            return (User)session.getAttribute(USER_SESSION_KEY);
        }

        return null;
    }
}
