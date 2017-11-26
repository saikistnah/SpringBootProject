package com.sai.user.controllerTest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private UserRegistrationController userRegistrationController;
	
	@Before
    public void setup() throws IOException {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).build();
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
		
		User mockuser = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk1@gmail.com");
		user.setBirthDate("26-may-1999");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(1);
		
		List<User> li = new ArrayList<>();
		li.add(mockuser);
		
		Mockito.when(userUtils.validateDate("26-may-1999")).thenReturn(true);
		Mockito.when(userService.getUsers()).thenReturn(li);
		
		this.mockMvc.perform(post("/create")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk()).
                andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
 
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
		List<User> li = new ArrayList<>();
		li.add(user);
		
		Map<String,String> mp = new HashMap<>();
		mp.put("resMsg", "Email already exists");
		mp.put("userId", Integer.toString(1));
		mp.put("valErrors", "Email already exists");
		
		
		Mockito.when(userUtils.validateDate("26-may-1999")).thenReturn(true);
		Mockito.when(userService.getUsers()).thenReturn(li);
		Mockito.when(customExceptionWithHttpStatusCode.errorHandle("Email already exists", 1, "Email already exists")).thenReturn( new ResponseEntity<Object>(mp,HttpStatus.CONFLICT));
		
		
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
		
		User mockuser = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk1@gmail.com");
		user.setBirthDate("26-may-1999");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(1);
		
		List<User> li = new ArrayList<>();
		li.add(mockuser);
		
		Mockito.when(userUtils.validateDate("26-may-1999")).thenReturn(true);
		Mockito.when(userService.getUsers()).thenReturn(li);
		
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
		
		User mockuser = new User();
		user.setfName("sai");
		user.setlName("keish");
		user.setEmail("sk1@gmail.com");
		user.setBirthDate("26-may-1999");
		user.setPinCode("12345");
		user.setActive(false);
		user.setId(1);
		
		List<User> li = new ArrayList<>();
		li.add(mockuser);
		
		Map<String,String> mp = new HashMap<>();
		mp.put("resMsg", "Please Enter valid Date");
		mp.put("userId", Integer.toString(1));
		mp.put("valErrors", "Please Enter valid Date");
		
		
		Mockito.when(userUtils.validateDate("26-may-1999")).thenReturn(false);
		Mockito.when(userService.getUsers()).thenReturn(li);
		Mockito.when(customExceptionWithHttpStatusCode.errorHandle("Please Enter valid Date", 1, "Please Enter valid Date")).thenReturn( new ResponseEntity<Object>(mp,HttpStatus.CONFLICT));
		
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
