package com.example.daily_issue.login.util;

import com.example.daily_issue.login.domain.Member;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {

    public static final String USER_SESSION_KEY = "sessiondUser";

    /**
     * nullPoint check
     * @param session
     * @return
     */
    public static boolean isLoginUser (HttpSession session) {
        Object loginiUser = session.getAttribute(USER_SESSION_KEY);

        if(loginiUser == null){
            return false;
        }
        return true;
    }

    public static Member getSessiondUser (HttpSession session){
        if(isLoginUser(session)){
            return (Member)session.getAttribute(USER_SESSION_KEY);
        }

        return null;
    }
}
