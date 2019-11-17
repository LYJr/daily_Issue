package com.example.daily_issue.calendar.security.aop;

import com.example.daily_issue.calendar.dao.repository.basic.BasicTaskRepository;
import com.example.daily_issue.calendar.domain.converter.TaskDomainConverter;
import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.calendar.security.service.SecurityService;
import com.example.daily_issue.login.domain.Member;
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
    BasicTaskRepository basicTaskRepository;

    @Autowired
    TaskDomainConverter taskMapper;



    @Around("@annotation(com.example.daily_issue.calendar.security.aop.EnableOwnerCheck) && args(taskId, ..)")
    public Object checkTaskOwner(ProceedingJoinPoint pjp, Long taskId) throws Throwable {
        Optional<BasicTaskEntity> taskResp = basicTaskRepository.findById(taskId);
        return taskResp.isPresent() ? checkTaskOwner(pjp, taskResp.get()) : returnRequestTypeEmptyValue(pjp);
    }

    @Around("@annotation(com.example.daily_issue.calendar.security.aop.EnableOwnerCheck) && args(basicTask)")
    public Object checkTaskOwner(ProceedingJoinPoint pjp, Optional<BasicTaskEntity> basicTask) throws Throwable {
        return basicTask.isPresent() ? checkTaskOwner(pjp, basicTask.get()) : returnRequestTypeEmptyValue(pjp);
    }

    @Around("@annotation(com.example.daily_issue.calendar.security.aop.EnableOwnerCheck) && args(basicTask)")
    public Object checkTaskOwner(ProceedingJoinPoint pjp, BasicTaskEntity basicTask) throws Throwable {

        // check isOwner (Task owner equals Logged User)
        /*Optional<String> userId = basicTask.getCreatedBy()
                .map(Member::getUserId)
                .filter(id -> id.equals(securityService.getMember().getUserId()));*/

        Member taskOwner = basicTask.getTaskOwner();
        if(taskOwner != null && taskOwner.getUserId().equals(securityService.getMember().getUserId()))
        {
            return pjp.proceed();
        }
        return returnRequestTypeEmptyValue(pjp);


        // is owner? / then process
        // is not owner? / then return null or empty value
        //return userId.isPresent() ? pjp.proceed() : returnRequestTypeEmptyValue(pjp);
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
