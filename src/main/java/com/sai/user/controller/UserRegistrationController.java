/**
 * 
 */
package com.sai.user.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sai.user.exception.CustomExceptionWithHttpStatusCode;
import com.sai.user.model.User;
import com.sai.user.service.UserService;
import com.sai.user.utils.UserUtils;

/**
 * @author Saikrishna Gudla
 *
 */

@RestController
public class UserRegistrationController {
	
	@Autowired
	CustomExceptionWithHttpStatusCode customExceptionWithHttpStatusCode;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserUtils userUtils;
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody User user){
		List<User> users = userService.getUsers();
		if(user.getBirthDate()!=null && userUtils.validateDate(user.getBirthDate())){
		Stream<User> strmUsr=users.stream().filter(s->s.getEmail().equals(user.getEmail())&& s.isActive());
		if(strmUsr.count()==0){
			user.setId(users.size()+1);
			user.setActive(true);
			users.add(user);
			userService.createUser(users);
			return new ResponseEntity<User>(users.get(users.size()-1),HttpStatus.OK);
		}else{
			return customExceptionWithHttpStatusCode.errorHandle("Email ALready Exists",users.size()+1,"Email ALready Exists");
		}
		}else{
			return customExceptionWithHttpStatusCode.errorHandle("Please Enter valid Date",users.size()+1,"Please Enter valid Date");
		}
		
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") int id,@RequestBody User user){
		List<User> users = userService.getUsers();
		if(user.getBirthDate()!=null && userUtils.validateDate(user.getBirthDate())){
		User usr = users.get(id-1);
		if(usr.getId() == id && usr.isActive()){
			usr.setBirthDate(user.getBirthDate());
			usr.setPinCode(user.getPinCode());
			users.remove(id-1);
			users.add(id-1, usr);
			userService.createUser(users);
			return new ResponseEntity<String>("updated sucessfully",HttpStatus.OK);
		}else{
			return customExceptionWithHttpStatusCode.errorHandle("user not available",id,"user not available");
		}
		
		}else{
			return customExceptionWithHttpStatusCode.errorHandle("Please Enter valid Date",users.size()+1,"Please Enter valid Date");
		}
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") int id){
		String message = null;
		List<User> users = userService.getUsers();
		User user = users.get(id-1);
		if(user.getId() == id && user.isActive()){
			user.setActive(false);
			users.remove(id-1);
			users.add(id-1, user);
			userService.createUser(users);
			message = "deleted sucessfully";
		}else{
			message = "user not available";
		}
		return message;
	}
	
}
