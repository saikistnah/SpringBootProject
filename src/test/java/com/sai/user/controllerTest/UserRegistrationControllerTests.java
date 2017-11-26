package com.sai.user.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sai.user.controller.UserRegistrationController;
import com.sai.user.exception.CustomExceptionWithHttpStatusCode;
import com.sai.user.model.User;
import com.sai.user.service.UserService;
import com.sai.user.testUtils.TestUtil;
import com.sai.user.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserRegistrationController.class,CustomExceptionWithHttpStatusCode.class,UserService.class,UserUtils.class})
public class UserRegistrationControllerTests {
	
	private MockMvc mockMvc;
	
	@Autowired
	CustomExceptionWithHttpStatusCode customExceptionWithHttpStatusCode;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserUtils userUtils;

	@Test
	public void create() throws IOException, Exception {
		User user = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk@gmail.com");
		user.setBirthDate("26-may-199");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(0);
		
		mockMvc.perform(post("/create")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
 
	}

}
