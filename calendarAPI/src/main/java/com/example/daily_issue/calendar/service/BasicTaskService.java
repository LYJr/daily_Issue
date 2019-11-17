package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.dao.repository.basic.BasicTaskRepository;
import com.example.daily_issue.calendar.domain.converter.TaskDomainConverter;
import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.calendar.domain.vo.req.BasicTaskReq;
import com.example.daily_issue.calendar.domain.vo.resp.BasicTaskResp;
import com.example.daily_issue.calendar.security.aop.EnableOwnerCheck;
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
     * {@link TaskDomainConverter} Domain과 RO객체 변환을 위한 mapper
     */
    @Autowired
    TaskDomainConverter taskMapper;

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
     * @param taskReq {@link BasicTaskReq} 저장 대상 RO
     *
     * @return {@link BasicTaskResp} basic task response RO
     */
    public Optional<BasicTaskResp> save(@NonNull BasicTaskReq taskReq)
    {
        BasicTaskEntity task = taskMapper.ReqToEntity(taskReq);

        // save
        BasicTaskEntity savedTask = task != null ? basicTaskRepository.save(task) : null;
        BasicTaskResp result = taskMapper.EntityToResp(savedTask);

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
     * @return {@link BasicTaskResp} 수정 적용된 basic task RO
     */
    public Optional<BasicTaskResp> update(@NonNull Long taskId, BasicTaskReq taskReq)
    {
        // get existing task
        Optional<BasicTaskEntity> originTask = findByTaskId(taskId);
        BasicTaskEntity task = taskMapper.ReqToEntity(taskReq, originTask);

        // update
        return task != null ? update(task) : Optional.empty();
    }

    /**
     * Update optional.
     * basic task 수정
     * domain 객체를 수정/적용한다.
     * {@link EnableOwnerCheck} 는 AOP에서 수정하려는 task의 creater와 로그인한 사용자가 올바른지 확인한다.
     *
     * @param task {@link BasicTaskEntity} 수정 대상 domain
     *
     * @return {@link BasicTaskResp} 수정 적용된 basic task RO
     */
    private Optional<BasicTaskResp> update(@NonNull BasicTaskEntity task)
    {
        // update
        BasicTaskEntity updatedTask = task != null ? basicTaskRepository.save(task) : null;
        BasicTaskResp result = taskMapper.EntityToResp(updatedTask);

        return Optional.ofNullable(result);
    }

    /**
     * Delete optional.
     * basic task를 삭제한다.
     * task pk를 받아서 삭제한다.
     * {@link EnableOwnerCheck} 는 AOP에서 수정하려는 task의 creater와 로그인한 사용자가 올바른지 확인한다.
     *
     * @param taskId 수정하려는 task의 pk
     *
     * @return {@link BasicTaskResp} 삭제 적용된 basic task RO
     */
    public Optional<BasicTaskResp> delete(@NonNull Long taskId)
    {
        // get existing task
        Optional<BasicTaskEntity> originTask = findByTaskId(taskId);

        return originTask.isPresent() ? delete(originTask.get()) : Optional.empty();
    }

    /**
     * Delete optional.
     * basic task를 삭제한다.
     * {@link EnableOwnerCheck} 는 AOP에서 수정하려는 task의 creater와 로그인한 사용자가 올바른지 확인한다.
     *
     * @param task {@link BasicTaskEntity} 삭제 대상 domain
     *
     * @return {@link BasicTaskResp} 삭제 적용된 basic task RO
     */
    private Optional<BasicTaskResp> delete(@NonNull BasicTaskEntity task)
    {
        Optional<BasicTaskEntity> originTask = Optional.ofNullable(task);
        originTask.ifPresent(t -> basicTaskRepository.delete(t));

        BasicTaskResp result = taskMapper.EntityToResp(originTask);

        return originTask.isPresent() ? Optional.ofNullable(result) : Optional.empty();
    }


    /**
     * Find by task id optional.
     * basic task를 검색한다.
     *
     * @param id 검색하려는 task의 pk
     *
     * @return {@link BasicTaskEntity} 검색된 basic task domain entity
     */
    protected Optional<BasicTaskEntity> findByTaskId(@NonNull Long id)
    {
        return basicTaskRepository.findById(id);
    }


}
