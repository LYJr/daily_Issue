package com.example.daily_issue.checklist;

import com.example.daily_issue.CheckListApplication;
import com.example.daily_issue.checklist.category.group.check.service.CheckDetail;
import com.example.daily_issue.checklist.category.group.service.TodoGroup;
import com.example.daily_issue.checklist.category.service.TodoGroupCategory;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = CheckListApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS) @TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CheckListApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MemberRepository memberRepository;

	TodoGroupCategory.Response categoryResponse =null;
	TodoGroup.Response groupResponse = null;
	CheckDetail.Response detailResponse = null;


	@BeforeAll
	public void beforeAll() {
		Member addAccount = new Member();
		addAccount.setUserId("kyoing");

		CheckListApplication.account = addAccount;
	}

	@Test
	@Order(0)
	public void addAccount() throws Exception {
		/** 계정 추가 **/
		memberRepository.save(CheckListApplication.account);
	}
	@Test
	@Order(1)
	public void integratedCreateTest() throws Exception{
		// 카테고리 생성
		TodoGroupCategory.Request categoryRequest = new TodoGroupCategory.Request();
		categoryRequest.setName("mainCategory");

		mockMvc.perform(
				post("/category/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(categoryRequest))
		)
				.andDo(print())
				.andExpect(item -> {
					String contentAsString = item.getResponse().getContentAsString();
					categoryResponse = objectMapper.readValue(contentAsString, TodoGroupCategory.Response.class);

					assertTrue(categoryResponse.getId() > 0);
					assertEquals(categoryResponse.getName(), categoryRequest.getName());
				});

		TodoGroup.Request groupRequest = new TodoGroup.Request();
		groupRequest.setContents("contents");
		groupRequest.setTitle("title");
		groupRequest.setTodoGroupCategoryId(categoryResponse.getId());

		mockMvc.perform(
				post("/group/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(groupRequest))
		)
				.andDo(print())
				.andExpect(item -> {
					String contentAsString = item.getResponse().getContentAsString();
					groupResponse = objectMapper.readValue(contentAsString, TodoGroup.Response.class);

					assertTrue(groupResponse.getId() > 0);
					assertEquals(groupResponse.getContents(), groupRequest.getContents());
					assertEquals(groupResponse.getTitle(), groupRequest.getTitle());
				});

		CheckDetail.Request detailRequest = new CheckDetail.Request();
		detailRequest.setComplete(false);
		detailRequest.setContents("contents");
		detailRequest.setTitle("detail title");
		detailRequest.setTodoGroupId(groupResponse.getId());

		mockMvc.perform(
				post("/group/"+ detailRequest.getTodoGroupId() +"/detail/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(detailRequest))
		)
				.andDo(print())
				.andExpect(item -> {
					String contentAsString = item.getResponse().getContentAsString();
					detailResponse = objectMapper.readValue(contentAsString, CheckDetail.Response.class);

					assertTrue(detailResponse.getId() > 0);
					assertEquals(detailRequest.getContents(), detailResponse.getContents());
					assertEquals(detailRequest.getTitle(), detailResponse.getTitle());
				});


		mockMvc.perform(
				post("/group/"+ detailRequest.getTodoGroupId() +"/detail/"+detailResponse.getId()+"/complete")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"complete\":true}")
		)
				.andDo(print())
				.andExpect(item -> {

					String contentAsString = item.getResponse().getContentAsString();
					detailResponse = objectMapper.readValue(contentAsString, CheckDetail.Response.class);

					assertTrue(detailResponse.isComplete());
				});
	}
}
