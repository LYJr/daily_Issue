package com.example.daily_issue.checklist.group.check.service.impl;

import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.check.service.CheckDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("checkDetailService")
public class CheckDetailServiceImpl implements CheckDetailService {
    private CheckDetailRepository checkDetailRepository;

    public CheckDetailServiceImpl(CheckDetailRepository checkDetailRepository) {
        this.checkDetailRepository = checkDetailRepository;
    }

    @Override
    public List<CheckDetail> findAll() {
        return checkDetailRepository.findAll();
    }

    @Override
    public CheckDetail save(CheckDetail checkDetail) {
        return checkDetailRepository.save(checkDetail);
    }
}
