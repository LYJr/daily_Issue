package com.example.daily_issue.calendar.aop;

import com.example.daily_issue.calendar.domain.RecordedTask;
import com.example.daily_issue.calendar.security.service.SecurityService;
import com.example.daily_issue.calendar.service.CalendarService;
import com.example.daily_issue.login.domain.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
@Aspect
public class OwnerChecker {

    @Autowired
    SecurityService securityService;
    @Autowired
    CalendarService calendarService;


    @Around("@annotation(EnableOwnerCheck) && args(taskId, ..)")
    public Object checkTaskOwner(ProceedingJoinPoint pjp, Long taskId) throws Throwable {
        Optional<RecordedTask> originTask = calendarService.findByTaskId(taskId);
        return originTask.isPresent() ? checkTaskOwner(pjp, originTask.get()) : returnRequestTypeEmptyValue(pjp);
    }

    @Around("@annotation(EnableOwnerCheck) && args(recordedTask)")
    public Object checkTaskOwner(ProceedingJoinPoint pjp, Optional<RecordedTask> recordedTask) throws Throwable {
        return recordedTask.isPresent() ? checkTaskOwner(pjp, recordedTask.get()) : returnRequestTypeEmptyValue(pjp);
    }


    @Around("@annotation(EnableOwnerCheck) && args(recordedTask)")
    public Object checkTaskOwner(ProceedingJoinPoint pjp, RecordedTask recordedTask) throws Throwable {

        // check isOwner (Task owner equals Logged User)
        Optional<String> userId = recordedTask.getCreatedBy()
                .map(Account::getUserId)
                .filter(id -> id.equals(securityService.getAccount().getUserId()));


        // is owner? / then process
        // is not owner? / then return null or empty value
        return userId.isPresent() ? pjp.proceed() : returnRequestTypeEmptyValue(pjp);

    }




    private Object returnRequestTypeEmptyValue(JoinPoint jp)
    {
        // get method information
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Class returnType = method.getReturnType();

        // return empty or null value
        return returnType.isAssignableFrom(Optional.class) ? Optional.empty() : null;
    }
}
