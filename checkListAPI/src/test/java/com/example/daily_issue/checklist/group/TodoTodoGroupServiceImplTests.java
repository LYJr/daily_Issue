package com.example.daily_issue.checklist.group;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.account.service.impl.AccountRepository;
import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import com.example.daily_issue.checklist.group.web.TodoGroupRestController;
import com.example.daily_issue.login.domain.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
public class TodoTodoGroupServiceImplTests {
    @Autowired
    private TodoGroupService groupService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    @Commit
    public void crudTests() {
        /** 계정 추가 **/
        Account addAccount = new Account();
        addAccount.setUserId("kyoing");

        accountRepository.save(addAccount);

        CheckListApplication.account = addAccount;

        /** 그룹 추가 **/
        TodoGroup addGroup = TodoGroup.builder()
                .title("Title")
                .contents("Contents")
                .build();
        addGroup.setCheckDetails(new ArrayList<>());
        addGroup.getCheckDetails().add(new CheckDetail().builder().title("hello").build());

        addGroup = groupService.save(addGroup);



        TodoGroup resultGroup = groupService.findById(addGroup.getId()).get();

        assertTrue(addGroup.getId() > 0);                // 그룹 체크
        assertEquals(resultGroup, addGroup);                      // 조회한 결과와 같은지 확인
        assertTrue(resultGroup.getCreatedDate().isPresent());     // 생성일이 있는지 확인
        assertTrue(resultGroup.getCreatedBy().isPresent());       // 만든이가 있는지 파



        resultGroup = groupService.findById(resultGroup.getId()).get();

        assertTrue(resultGroup.getCheckDetails().size() > 0);
        assertTrue(resultGroup.getCheckDetails().get(0).getTitle().equals("hello"));
        assertFalse(resultGroup.getCheckDetails().get(0).isComplete());
    }

    @Test @Transactional @Commit
    public void crudUsingWeb() throws Exception {
        /** 계정 추가 **/
        Account addAccount = new Account();
        addAccount.setUserId("kyoing");

        accountRepository.save(addAccount);

        CheckListApplication.account = addAccount;

        List<CheckDetail.Request> checkDetails = new LinkedList<>();
        checkDetails.add(CheckDetail.Request.builder().title("false-a").contents("con-a").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("true-b").contents("con-b").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("false-c").contents("con-c").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("true-d").contents("con-d").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("true-e").contents("con-e").complete(false).build());

        /** 그룹 추가 **/
        TodoGroup.Request addGroup = TodoGroup.Request.builder()
                .title("Title")
                .contents("Contents")
                .checkDetails(checkDetails)
                .build();
        System.out.println(objectMapper.writeValueAsString(addGroup));
        mockMvc.perform(
                post("/group/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(addGroup))

        )
                .andExpect(item ->
                        {
                            String body = item.getResponse().getContentAsString();
                            TodoGroup.Response response = objectMapper.readValue(body, TodoGroup.Response.class);

                            List<CheckDetail> responseCheckDetails = response.getCheckDetails();

                            assertTrue(response.getId() > 0);
                            assertTrue(response.getContents().equals(addGroup.getContents()));
                            assertTrue(response.getTitle().equals(addGroup.getTitle()));

                            for(CheckDetail checkDetail : responseCheckDetails) {
                                assertTrue(findDetailInList(checkDetail, checkDetails));
                            }
                        }
                )
                .andDo(print());
    }


    private boolean findDetailInList(CheckDetail source, List<CheckDetail.Request> dest) {
        for(CheckDetail.Request checkDetail : dest) {
            if(
                    checkDetail.getContents().equals(source.getContents()) &&
                    checkDetail.getTitle().equals(source.getTitle())
            ) {
                return true;
            }
        }
        return false;
    }
}
