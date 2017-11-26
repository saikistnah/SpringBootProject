package com.sai.user.controllerTest;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sai.user.controller.UserRegistrationController;
import com.sai.user.exception.CustomExceptionWithHttpStatusCode;
import com.sai.user.model.User;
import com.sai.user.service.UserService;
import com.sai.user.testUtils.TestUtil;
import com.sai.user.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK,classes = {UserRegistrationController.class,CustomExceptionWithHttpStatusCode.class,UserService.class,UserUtils.class,TestContext.class})
public class UserRegistrationControllerTests {
	
	private MockMvc mockMvc;
	
	@MockBean
	CustomExceptionWithHttpStatusCode customExceptionWithHttpStatusCode;
	
	@MockBean
	UserService userService;
	
	@MockBean
	UserUtils userUtils;
	
	@InjectMocks
    private UserRegistrationController abc;
	
	@Before
    public void setup() throws IOException {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(abc).build();
    }

	@Test
	public void create() throws IOException, Exception {
		User user = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk@gmail.com");
		user.setBirthDate("26-may-1999");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(0);
		
		this.mockMvc.perform(post("/create")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk());
 
	}
	
	@Test
	public void createWithExistingEmail() throws IOException, Exception {
		User user = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk@gmail.com");
		user.setBirthDate("26-may-1999");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(0);
		
		this.mockMvc.perform(post("/create")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk());
 
	}
	
	@Test
	public void update() throws IOException, Exception {
		User user = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk@gmail.com");
		user.setBirthDate("26-may-199");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(0);
		
		this.mockMvc.perform(put("/update/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk());
 
	}
	
	@Test
	public void updateWithInvalidDate() throws IOException, Exception {
		User user = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk@gmail.com");
		user.setBirthDate("26-may-199");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(0);
		
		mockMvc.perform(put("/update/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk());
 
	}
	
	@Test
	public void Delete() throws IOException, Exception {
		
		this.mockMvc.perform(delete("/delete/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
 
	}
	
	@Test
	public void deleteInvalidUser() throws IOException, Exception {
		this.mockMvc.perform(delete("/delete/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isConflict());
 
	}

}
