package com.example.daily_issue.checklist.group;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.account.service.impl.AccountRepository;
import com.example.daily_issue.checklist.group.service.Group;
import com.example.daily_issue.checklist.group.service.GroupService;
import com.example.daily_issue.login.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CheckListApplication.class)
@Slf4j
public class GroupServiceImplTests {
    @Autowired
    private GroupService groupService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void crudTests() {
        Account addAccount = new Account();
        addAccount.setUserId("kyoing");

        accountRepository.save(addAccount);

        Group addGroup = Group.builder()
                .title("Title")
                .contents("Contents")
                .build();

        addGroup = groupService.save(addGroup);
        assertTrue(addGroup.getId() > 0);

        Optional<Group> optinalFoundGroup = groupService.findById(addGroup.getId());
        assertNotNull(optinalFoundGroup.get());

        Group resultGroup = optinalFoundGroup.get();
        assertNotNull(resultGroup);
        assertEquals(resultGroup, addGroup);
        assertTrue(resultGroup.getCreatedDate().isPresent());
        assertTrue(resultGroup.getCreatedBy().isPresent());

        log.info(resultGroup.toString());
        log.info(addGroup.toString());
        log.info(resultGroup.getCreatedDate()+"");
    }
}
