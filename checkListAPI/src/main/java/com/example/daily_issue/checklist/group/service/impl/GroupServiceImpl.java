package com.example.daily_issue.checklist.group.service.impl;

import com.example.daily_issue.checklist.group.service.Group;
import com.example.daily_issue.checklist.group.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("groupService")
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Optional<Group> findById(Integer id) {
        return groupRepository.findById(id);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }
}
