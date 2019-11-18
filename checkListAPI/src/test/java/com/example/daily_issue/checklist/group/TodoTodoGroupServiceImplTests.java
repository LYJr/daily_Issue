package com.example.daily_issue.checklist.group;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.group.service.TodoGroup;
import com.example.daily_issue.checklist.group.service.TodoGroupService;
import com.example.daily_issue.checklist.member.service.impl.MemberRepository;
import com.example.daily_issue.login.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CheckListApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS) @TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TodoTodoGroupServiceImplTests {
    @Autowired
    private TodoGroupService groupService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    List<CheckDetail.Request> checkDetails = new LinkedList<>();
    String responseBody = null;
    @BeforeAll
    public void beforeAll() {
        Member addAccount = new Member();
        addAccount.setUserId("kyoing");

        CheckListApplication.account = addAccount;

        checkDetails.add(CheckDetail.Request.builder().title("false-a").contents("con-a").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("true-b").contents("con-b").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("false-c").contents("con-c").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("true-d").contents("con-d").complete(false).build());
        checkDetails.add(CheckDetail.Request.builder().title("true-e").contents("con-e").complete(false).build());
    }

    @Test @Transactional @Commit
    @Order(0)
    public void addAccount() throws Exception {
        /** 계정 추가 **/
        memberRepository.save(CheckListApplication.account);
    }

    @Test @Transactional @Commit
    @Order(1)
    public void addGrop() throws Exception {
        /** 그룹 추가 **/
        TodoGroup.Request addGroup = TodoGroup.Request.builder()
                .title("Title")
                .contents("Contents")
                .checkDetails(checkDetails)
                .build();

        responseBody = mockMvc.perform(
                post("/group/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(addGroup))

        )
                .andDo(print())
                .andExpect(item ->
                        {
                            String body = item.getResponse().getContentAsString();
                            TodoGroup.Response response = objectMapper.readValue(body, TodoGroup.Response.class);

                            List<CheckDetail.Response> responseCheckDetails = response.getCheckDetails();

                            assertTrue(response.getId() > 0);
                            assertTrue(response.getContents().equals(addGroup.getContents()));
                            assertTrue(response.getTitle().equals(addGroup.getTitle()));

                            if (responseCheckDetails != null) {
                                for (CheckDetail.Response checkDetail : responseCheckDetails) {
                                    assertTrue(findDetailInList(checkDetail, checkDetails));
                                }
                            }
                        }
                )
                .andReturn().getResponse().getContentAsString();
    }
    @Test @Transactional @Commit
    @Order(2)
    public void notFoundTest() throws Exception {
        mockMvc.perform(get("/group/" + Integer.MAX_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test @Transactional @Commit
    @Order(3)
    public void findView() throws Exception {
        TodoGroup.Response saveResponse = objectMapper.readValue(responseBody, TodoGroup.Response.class);
        mockMvc.perform(
                get("/group/" + saveResponse.getId())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(item -> {
                    String body = item.getResponse().getContentAsString();
                    TodoGroup.Response response = objectMapper.readValue(body, TodoGroup.Response.class);

                    List<CheckDetail.Response> responseCheckDetails = saveResponse.getCheckDetails();

                    assertTrue(response.getId() > 0);
                    assertTrue(response.getContents().equals(saveResponse.getContents()));
                    assertTrue(response.getTitle().equals(saveResponse.getTitle()));
                    if (responseCheckDetails != null) {
                        for (CheckDetail.Response checkDetail : responseCheckDetails) {
                            assertTrue(findDetailInList(checkDetail, checkDetails));
                        }
                    }
                });
    }

    @Test @Transactional @Commit
    @Order(4)
    public void update() throws Exception {
        TodoGroup.Response saveResponse = objectMapper.readValue(responseBody, TodoGroup.Response.class);
        saveResponse.setTitle("updateString");
        log.info("start Update");
        mockMvc.perform(
                post("/group/" + saveResponse.getId())
                        .content(objectMapper.writeValueAsString(saveResponse))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test @Transactional @Commit
    @Order(5)
    public void findViewAfterUpdate() throws Exception{
        TodoGroup.Response saveResponse = objectMapper.readValue(responseBody, TodoGroup.Response.class);
        mockMvc.perform(
                get("/group/" + saveResponse.getId())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(item -> {
                    String body = item.getResponse().getContentAsString();
                    TodoGroup.Response response = objectMapper.readValue(body, TodoGroup.Response.class);

                    assertEquals(response.getTitle(), saveResponse.getTitle());
                });

    }


    private boolean findDetailInList(CheckDetail.Response source, List<CheckDetail.Request> dest) {
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
