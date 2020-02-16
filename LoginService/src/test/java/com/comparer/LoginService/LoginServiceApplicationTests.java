package com.comparer.LoginService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.comparer.LoginService.controller.LoginController;
import com.comparer.LoginService.model.LoginCredentials;
import com.comparer.LoginService.repository.LoginRepository;
import com.comparer.LoginService.resourceobject.SuccessReport;
import com.comparer.LoginService.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class LoginServiceApplicationTests {

	@InjectMocks
	LoginController loginController;

	@Mock
	LoginCredentials loginCredentials;

	@Mock
	private DataService dataService;

	@Mock
	LoginRepository loginRepository;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void checkInputNotNull() {
		assertEquals(false, loginController.validateInput("", ""));
		assertEquals(false, loginController.validateInput(null, null));
		assertEquals(false, loginController.validateInput("username", ""));
		assertEquals(false, loginController.validateInput("", null));
		assertEquals(true, loginController.validateInput("username", "password"));
	}

	@Test
	void checkLoginStatus() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/status").accept(MediaType.TEXT_HTML_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals("Service is running !!", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void checkcheckUsernameExist() throws Exception {
		String username = "nitishsr";
		Mockito.when(dataService.checkUsernameTaken(username)).thenReturn(true);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/checkUserExist").param("username", username)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		SuccessReport succesReport = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				SuccessReport.class);
		assertEquals("USEREXIST", succesReport.getStatusCode());

	}

	@Test
	void addUserCredTest() throws Exception {
		String username = "nitishsr";
		String password = "Welcome1";
		LoginCredentials login = new LoginCredentials();
		login.setUsername(username);
		login.setPassword(password);
		Mockito.when(dataService.addUser(username, password)).thenReturn(login);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/addUser").param("username", username)
				.param("password", password).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		SuccessReport succesReport = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				SuccessReport.class);
		assertEquals("USERNOTADDED", succesReport.getStatusCode());

	}

}
