package com.example.daily_issue.checklist.group.check.web;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.group.TodoTodoGroupServiceImplTests;
import com.example.daily_issue.checklist.category.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.category.group.service.TodoGroup;
import com.example.daily_issue.checklist.category.group.service.TodoGroupService;
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

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        todoTodoGroupServiceImplTests = new TodoTodoGroupServiceImplTests();
        todoTodoGroupServiceImplTests.setGroupService(todoGroupService);
        todoTodoGroupServiceImplTests.setMemberRepository(memberRepository);
        todoTodoGroupServiceImplTests.setObjectMapper(objectMapper);
        todoTodoGroupServiceImplTests.setMockMvc(mockMvc);
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
        if(todoTodoGroupServiceImplTests.responseBody == null) {
            todoTodoGroupServiceImplTests.addGrop();
        }
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

                    assertTrue(responseResult.getTitle().equals(checkDetailRequest.getTitle()));
                    assertTrue(responseResult.getContents().equals(checkDetailRequest.getContents()));
                })
                .andReturn().getResponse().getContentAsString()
                ;
    }

    @Test
    @Transactional @Commit @Order(2)
    void list() throws Exception {
        int random = new Random().nextInt(10);
        for(int i = 0; i<random; i++) {
            save();
        }

        TodoGroup.Response response = objectMapper.readValue(todoTodoGroupServiceImplTests.responseBody, TodoGroup.Response.class);
        mockMvc.perform(
                get("/group/"+response.getId()+"/detail/")
        )       .andDo(print())
                .andExpect(item -> {
                    CheckDetail.Response arr[] = objectMapper.readValue(item.getResponse().getContentAsString(), CheckDetail.Response[].class);

                    assertTrue(arr.length == random);
                    for(CheckDetail.Response checkResponse : arr) {
                        assertFalse(checkResponse.isComplete());
                        assertEquals(checkResponse.getContents(), "con");
                        assertEquals(checkResponse.getTitle(), "tit");
                    }
                });
    }


    @Test
    @Transactional @Commit @Order(3)
    void update() throws Exception {
        TodoGroup.Response todogroupResponse = objectMapper.readValue(todoTodoGroupServiceImplTests.responseBody, TodoGroup.Response.class);
        String contentAsString = mockMvc.perform(
                get("/group/" + todogroupResponse.getId() + "/detail/")
        ).andReturn().getResponse().getContentAsString();

        CheckDetail.Response arr[] = objectMapper.readValue(contentAsString, CheckDetail.Response[].class);

        CheckDetail.Response detailResponse = arr[0];

        CheckDetail.Request request = new CheckDetail.Request();
        request.setTitle("gigigidingo");
        request.setContents("dingo");
        mockMvc.perform(
                post("/group/"+todogroupResponse.getId()+"/detail/"+detailResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(item -> {
                    CheckDetail.Response response = objectMapper.readValue(item.getResponse().getContentAsString(), CheckDetail.Response.class);

                    assertEquals(response.getContents(), request.getContents());
                    assertEquals(response.getTitle(), request.getTitle());
                });
    }

    @Test
    @Transactional @Commit @Order(4)
    void updateComplete() throws Exception {
        TodoGroup.Response response = objectMapper.readValue(todoTodoGroupServiceImplTests.responseBody, TodoGroup.Response.class);

        String contentAsString = mockMvc.perform(
                get("/group/" + response.getId() + "/detail/")
        ).andReturn().getResponse().getContentAsString();

        CheckDetail.Response arr[] = objectMapper.readValue(contentAsString, CheckDetail.Response[].class);

        TodoGroup.Response todogroupResponse = objectMapper.readValue(todoTodoGroupServiceImplTests.responseBody, TodoGroup.Response.class);
        CheckDetail.Response detailResponse = arr[0];

        mockMvc.perform(
                post("/group/"+todogroupResponse.getId()+"/detail/"+detailResponse.getId()+"/complete")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"complete\":true}")
        )
                .andDo(print())
                .andExpect(item -> {
                    CheckDetail.Response checkResponse = objectMapper.readValue(item.getResponse().getContentAsString(), CheckDetail.Response.class);

                    assertTrue(checkResponse.isComplete());
                });
    }
}