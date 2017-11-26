/**
 * 
 */
package com.sai.user.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sai.user.model.User;

/**
 * @author Saikrishna Gudla
 *
 */

@Component
public class UserService {
	
	public List<User> getUsers(){
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
		try {
			List<User> users = mapper.readValue(inputStream,typeReference);
			System.out.println("Users Saved!"+users.size());
			return users;
		} catch (IOException e){
			System.out.println("Unable to save users: " + e.getMessage());
		}
		return null;
	}
	
	public String createUser(List<User> users){
		ObjectMapper mapper = new ObjectMapper();
		try {
			 mapper.writeValue(new File("C:/Users/NEETHU/Downloads/userRegistration/userRegistration/src/main/resources/json/users.json"), users);
			 System.out.println("--Done--");
		} catch (IOException e){
			System.out.println("Unable to save users: " + e.getMessage());
		}
		return "Done!";
	}

}
