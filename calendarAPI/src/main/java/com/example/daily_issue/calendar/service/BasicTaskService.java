package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.aop.EnableOwnerCheck;
import com.example.daily_issue.calendar.dao.BasicTaskRepository;
import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.mapper.TaskMapper;
import com.example.daily_issue.calendar.ro.BasicTaskReq;
import com.example.daily_issue.calendar.ro.BasicTaskResp;
import com.example.daily_issue.calendar.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-09
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-09)
 */
/**
 * 단순일정을 위한 CRUD Service
 */
@Service
class BasicTaskService {

    /**
     * The Task mapper.
     * {@link TaskMapper} Domain과 RO객체 변환을 위한 mapper
     */
    @Autowired
    TaskMapper taskMapper;

    /**
     * The Security service.
     * {@link SecurityService} 사용자 인증정보를 얻기 위한 security service
     */
    @Autowired
    SecurityService securityService;

    /**
     * The Basic task repository.
     * {@link BasicTaskRepository} 단순일정 CRUD를 위한 repository
     */
    @Autowired
    BasicTaskRepository basicTaskRepository;


    /**
     * Save optional.
     * basic task 저장
     *
     * @param task {@link BasicTask} 저장 대상 domain
     *
     * @return {@link BasicTask} basic task domain entity
     */
    public Optional<BasicTaskResp> save(@NonNull BasicTaskReq taskReq)
    {
        BasicTask task = taskMapper.convertTaskReqToTask(taskReq);

        // save
        BasicTask savedTask = task != null ? basicTaskRepository.save(task) : null;
        BasicTaskResp result = taskMapper.convertTaskToTaskResp(savedTask);

        return Optional.ofNullable(result);
    }

    /**
     * Update optional.
     * basic task 수정
     * pk값으로 대상 basic task를 가져오고... 수정하려는 값을 basic task req (ro) 객체로 받는다.
     * {@link EnableOwnerCheck} 는 AOP에서 수정하려는 task의 creater와 로그인한 사용자가 올바른지 확인한다.
     *
     * @param taskId  수정하려는 task의 pk
     * @param taskReq {@link BasicTaskReq} 수정하려는 task의 RO 객체
     *
     * @return {@link BasicTask} 수정 적용된 basic task domain entity
     */
    @EnableOwnerCheck
    public Optional<BasicTask> update(@NonNull Long taskId, BasicTaskReq taskReq)
    {
        // get existing task
        Optional<BasicTask> originTask = findByTaskId(taskId);
        BasicTask task = taskMapper.convertTaskReqToTask(taskReq, originTask);

        // update
        return task != null ? update(task) : Optional.empty();
    }

    /**
     * Update optional.
     * basic task 수정
     * domain 객체를 수정/적용한다.
     * {@link EnableOwnerCheck} 는 AOP에서 수정하려는 task의 creater와 로그인한 사용자가 올바른지 확인한다.
     *
     * @param task {@link BasicTask} 수정 대상 domain
     *
     * @return {@link BasicTask} 수정 적용된 basic task domain entity
     */
    @EnableOwnerCheck
    public Optional<BasicTask> update(@NonNull BasicTask task)
    {
        // update
        BasicTask result = task != null ? basicTaskRepository.save(task) : task;
        return Optional.ofNullable(result);
    }

    /**
     * Delete optional.
     * basic task를 삭제한다.
     * task pk를 받아서 삭제한다.
     * {@link EnableOwnerCheck} 는 AOP에서 수정하려는 task의 creater와 로그인한 사용자가 올바른지 확인한다.
     *
     * @param id 수정하려는 task의 pk
     *
     * @return {@link BasicTask} 삭제 적용된 basic task domain entity
     */
    @EnableOwnerCheck
    public Optional<BasicTask> delete(@NonNull Long id)
    {
        // get existing task
        Optional<BasicTask> originTask = findByTaskId(id);

        // delete
        originTask.ifPresent(a -> basicTaskRepository.deleteById(a.getId()));

        return originTask.isPresent() ? originTask : Optional.empty();
    }

    /**
     * Delete optional.
     * basic task를 삭제한다.
     * {@link EnableOwnerCheck} 는 AOP에서 수정하려는 task의 creater와 로그인한 사용자가 올바른지 확인한다.
     *
     * @param task {@link BasicTask} 삭제 대상 domain
     *
     * @return {@link BasicTask} 삭제 적용된 basic task domain entity
     */
    @EnableOwnerCheck
    public Optional<BasicTask> delete(@NonNull BasicTask task)
    {
        Optional<BasicTask> originTask = Optional.ofNullable(task);
        originTask.ifPresent(t -> basicTaskRepository.delete(t));

        return originTask.isPresent() ? originTask : Optional.empty();
    }


    /**
     * Find by task id optional.
     * basic task를 검색한다.
     *
     * @param id 검색하려는 task의 pk
     *
     * @return {@link BasicTask} 검색된 basic task domain entity
     */
    public Optional<BasicTask> findByTaskId(@NonNull Long id)
    {
        return basicTaskRepository.findById(id);
    }


}
