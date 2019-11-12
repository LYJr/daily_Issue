package com.example.daily_issue.calendar.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

    @Autowired
    SecurityService securityService;

    @EventListener(classes = AuthenticationSuccessEvent.class)
    public void loginSuccessPostProcess(AbstractAuthenticationEvent event)
    {
        String userid = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();

        securityService.setMember(userid);
    }

}
