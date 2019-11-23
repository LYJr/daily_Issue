package com.example.daily_issue.checklist.category.web;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.category.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.category.group.service.TodoGroup;
import com.example.daily_issue.checklist.category.service.TodoGroupCategory;
import com.example.daily_issue.checklist.member.service.impl.MemberRepository;
import com.example.daily_issue.login.domain.Member;
import com.example.daily_issue.login.domain.QMember;
import com.fasterxml.jackson.core.JsonProcessingException;
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
class TodoGroupCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    private TodoGroupCategory.Request saveRequest;
    private TodoGroupCategory.Response saveResponse;

    @BeforeAll
    public void beforeAll() {
        Member addAccount = new Member();
        addAccount.setUserId("kyoing");

        CheckListApplication.account = addAccount;
    }

    @Test @Transactional
    @Commit
    @Order(0)
    public void addAccount() throws Exception {
        /** 계정 추가 **/
        memberRepository.save(CheckListApplication.account);
    }

    @Test
    @Order(1)
    void save() throws Exception {
        saveRequest = new TodoGroupCategory.Request();
        saveRequest.setName("category");

        mockMvc.perform(
                post("/category/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(saveRequest))
        )
                .andDo(print())
                .andExpect(item -> {
                    this.saveResponse = objectMapper.readValue(item.getResponse().getContentAsString(), TodoGroupCategory.Response.class);
                    assertTrue(this.saveResponse.getId() > 0);
                    assertEquals(this.saveResponse.getName(), this.saveRequest.getName());
                });
    }

    @Test
    @Order(2)
    void list() throws Exception {
        int random = new Random().nextInt(10);
        for(int i = 0; i<random; i++) {
            save();
        }

        mockMvc.perform(
                get("/category/")
        )
                .andDo(print())
                .andExpect(item -> {
                    String contentAsString = item.getResponse().getContentAsString();
                    TodoGroupCategory.Response[] responses = objectMapper.readValue(contentAsString, TodoGroupCategory.Response[].class);
                    assertTrue(responses.length == random);
                    for(TodoGroupCategory.Response response : responses) {
                        assertEquals(response.getName(), "category");
                    }
                });
    }

    @Test
    @Order(3)
    void view() throws Exception {
        save();
        mockMvc.perform(
                get("/category/" + saveResponse.getId())
        )
                .andDo(print())
                .andExpect(item -> {
                    String contentAsString = item.getResponse().getContentAsString();
                    TodoGroupCategory.Response responses = objectMapper.readValue(contentAsString, TodoGroupCategory.Response.class);

                    assertEquals(responses.getId(), saveResponse.getId());
                    assertEquals(responses.getName(), saveResponse.getName());
                });
    }

    @Test
    void update() throws Exception {
        save();

        TodoGroupCategory.Request request = new TodoGroupCategory.Request();
        request.setName("update Category");
        mockMvc.perform(
                post("/category/"+saveResponse.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(item -> {
                    String contentAsString = item.getResponse().getContentAsString();
                    TodoGroupCategory.Response responses = objectMapper.readValue(contentAsString, TodoGroupCategory.Response.class);

                    assertEquals(responses.getId(), saveResponse.getId());
                    assertEquals(responses.getName(), request.getName());
                });
    }
}