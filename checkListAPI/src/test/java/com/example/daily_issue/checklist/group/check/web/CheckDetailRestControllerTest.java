package com.example.daily_issue.checklist.group.check.web;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.group.TodoTodoGroupServiceImplTests;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(classes = CheckListApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS) @TestMethodOrder(MethodOrderer.Alphanumeric.class)
class CheckDetailRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TodoGroupService todoGroupService;
    @Autowired
    private ObjectMapper objectMapper;

    private TodoTodoGroupServiceImplTests todoTodoGroupServiceImplTests;

    String responseBody;

    @BeforeAll
    public void beforeAll() {
        Member addAccount = new Member();
        addAccount.setUserId("kyoing");

        CheckListApplication.account = addAccount;

        todoTodoGroupServiceImplTests = new TodoTodoGroupServiceImplTests(todoGroupService, memberRepository, objectMapper, mockMvc);
    }

    @Test @Transactional
    @Commit
    @Order(0)
    public void addAccount() throws Exception {
        /** 계정 추가 **/
        memberRepository.save(CheckListApplication.account);
    }

    @Test
    @Transactional @Commit @Order(1)
    void save() throws Exception {
        todoTodoGroupServiceImplTests.addGrop();

        TodoGroup.Response response = objectMapper.readValue(todoTodoGroupServiceImplTests.responseBody, TodoGroup.Response.class);

        CheckDetail.Request checkDetailRequest = CheckDetail.Request.builder()
                .complete(false)
                .contents("con")
                .todoGroupId(response.getId())
                .title("tit")
                .build();

        responseBody = mockMvc.perform(
                post("/group/"+response.getId()+"/detail/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(checkDetailRequest))
        )
                .andDo(print())
                .andExpect(item -> {
                    String responseBody = item.getResponse().getContentAsString();
                    CheckDetail.Response responseResult = objectMapper.readValue(responseBody, CheckDetail.Response.class);

                    assertTrue(responseResult.getId() > 0);
                    assertTrue(responseResult.getTitle().equals(checkDetailRequest.getTitle()));
                    assertTrue(responseResult.getContents().equals(checkDetailRequest.getContents()));
                })
                .andReturn().getResponse().getContentAsString()
                ;
    }

    @Test
    void list() {
    }


    @Test
    void update() {
    }

    @Test
    void updateComplete() {
    }
}