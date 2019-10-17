package com.example.daily_issue.checklist.group;

import com.example.daily_issue.checklist.common.service.CommonModel;
import com.example.daily_issue.checklist.group.service.Group;
import com.example.daily_issue.checklist.group.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GroupServiceImplTests {
    @Autowired
    private GroupService groupService;
    @Test
    public void crudTests() {
        Group addGroup = Group.builder()
                .title("Title")
                .contents("Contents")
                .build();

        addGroup = groupService.save(addGroup);
        assertTrue(addGroup.getGroupId() > 0);

        Optional<Group> optinalFoundGroup = groupService.findById(addGroup.getGroupId());
        assertNotNull(optinalFoundGroup.get());

        Group resultGroup = optinalFoundGroup.get();
        assertNotNull(resultGroup);
        assertEquals(resultGroup, addGroup);

        log.info(resultGroup.toString());
        log.info(addGroup.toString());
        log.info(resultGroup.getCreatedDate()+"");
    }
}
