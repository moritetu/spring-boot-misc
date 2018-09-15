package com.example.demo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import sample.web.database.security.SpringSecurityDatabaseAuthenticationApplication;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSecurityDatabaseAuthenticationApplication.class)
@AutoConfigureMockMvc
public class SpringSecurityDatabaseAuthenticationApplicationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testRequireLogin() throws Exception {
		mvc.perform(get("/user/home"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"))
			.andExpect(unauthenticated());
	}
	
	@Test
	public void testAccessDenied() throws Exception {
		MvcResult result = mvc.perform(
				formLogin().user("guest").password("guest"))
				.andExpect(authenticated().withRoles("USER"))
				.andExpect(status().isFound())
				.andReturn();
		HttpSession session = result.getRequest().getSession();
		mvc.perform(get(result.getResponse().getRedirectedUrl()).session((MockHttpSession)session))
			.andExpect(status().isForbidden());
	}

	@Test
	public void testFormLoginOnSuccess() throws Exception {
		mvc.perform(
			formLogin().user("admin").password("admin"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/user/home"))
			.andExpect(authenticated().withRoles("ADMIN"))
			;
	}

	@Test
	public void testFormLoginOnFailure() throws Exception {
		mvc.perform(
			formLogin().user("admin").password("dummy"))
			.andExpect(status().isFound())
			.andExpect(unauthenticated());
	}
}
