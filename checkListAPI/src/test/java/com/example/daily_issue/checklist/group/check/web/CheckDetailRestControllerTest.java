package com.example.daily_issue.checklist.group.check.web;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.account.service.impl.MemberRepository;
import com.example.daily_issue.checklist.group.check.service.CheckDetail;
import com.example.daily_issue.login.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
class CheckDetailRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository accountRepository;
    @Test
    void crudTestUsingWeb() throws Exception {
        /** 계정 추가 **/
        Member addAccount = new Member();
        addAccount.setUserId("kyoing");

        accountRepository.save(addAccount);

        CheckListApplication.account = addAccount;

        mockMvc.perform(
                get("/group/detail/2")
        ).andDo(print());

        CheckDetail.Request checkDetail = new CheckDetail.Request();
        checkDetail.setTitle("need");
        checkDetail.setContents("need2");

        mockMvc.perform(
                post("/group/detail/2")
                .content(objectMapper.writeValueAsString(checkDetail))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(item -> {
                   String responseBody = item.getResponse().getContentAsString();
                   CheckDetail.Response response = objectMapper.readValue(responseBody, CheckDetail.Response.class);

                   assertTrue(response.getTodoGroup().getId() > 0);
                });
    }
}